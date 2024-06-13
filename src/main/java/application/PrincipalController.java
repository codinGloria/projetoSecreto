package application;

import javafx.scene.control.*;
import model.Funcionario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.Locale;

public class PrincipalController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("testeProthera");
        em = emf.createEntityManager();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDataNasc.setCellValueFactory(new PropertyValueFactory<>("dataNascimento"));
        colSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        colFuncao.setCellValueFactory(new PropertyValueFactory<>("funcao"));

        colSalario.setCellFactory(column -> new TableCell<Funcionario, BigDecimal>() {
            private final NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));

            {
                nf.setMinimumFractionDigits(2);
                nf.setMaximumFractionDigits(2);
            }

            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(nf.format(item));
                }
            }
        });

        listarTudo();
    }

    @FXML
    public Button btnCadastrar;
    @FXML
    public Button btnRemover;
    @FXML
    public MenuItem btnListarTudo;
    @FXML
    public MenuItem btnListASC;
    @FXML
    public MenuItem btnAgrupar;
    @FXML
    public MenuItem btnAgruparFuncao;
    @FXML
    public Button btnAumentoSalario;
    @FXML
    public Button btnAniversariantes;
    @FXML
    public Button btnMaisVelho;
    @FXML
    public Button btnTotalSalario;
    @FXML
    public Button btnQntdSalarios;

    @FXML
    public TableView<Funcionario> tableView;
    @FXML
    public TableColumn<Funcionario, Integer> colId;
    @FXML
    public TableColumn<Funcionario, String> colNome;
    @FXML
    public TableColumn<Funcionario, LocalDate> colDataNasc;
    @FXML
    public TableColumn<Funcionario, BigDecimal> colSalario;
    @FXML
    public TableColumn<Funcionario, String> colFuncao;

    private EntityManager em;

    @FXML
    private void cadastrar() {
        em.getTransaction().begin();
        Funcionario Maria = new Funcionario(null, "Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador");
        Funcionario Joao = new Funcionario(null, "João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador");
        Funcionario Caio = new Funcionario(null, "Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador");
        Funcionario Miguel = new Funcionario(null, "Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor");
        Funcionario Alice =new Funcionario(null, "Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista");
        Funcionario Heitor = new Funcionario(null, "Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador");
        Funcionario Arthur = new Funcionario(null, "Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador");
        Funcionario Laura = new Funcionario(null, "Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente");
        Funcionario Heloisa = new Funcionario(null, "Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista");
        Funcionario Helena = new Funcionario(null, "Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente");

        em.persist(Maria);
        em.persist(Joao);
        em.persist(Caio);
        em.persist(Miguel);
        em.persist(Alice);
        em.persist(Heitor);
        em.persist(Arthur);
        em.persist(Laura);
        em.persist(Heloisa);
        em.persist(Helena);

        em.getTransaction().commit();
        atualizarTabela();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cadastro de Funcionários");
        alert.setHeaderText(null);
        alert.setContentText("Funcionários cadastrados com sucesso!");
        alert.showAndWait();
    }

    @FXML
    private void remover() {
        em.getTransaction().begin();
        Funcionario joao = em.createQuery("SELECT f FROM Funcionario f WHERE f.nome = 'João'", Funcionario.class).getSingleResult();
        if (joao != null) {
            em.remove(joao);
        }
        em.getTransaction().commit();
        atualizarTabela();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Remover Funcionários");
        alert.setHeaderText(null);
        alert.setContentText("Usuário: João removido com sucesso!");
        alert.showAndWait();
    }

    @FXML
    private void listarTudo() {
        List<Funcionario> funcionarios = em.createQuery("SELECT f FROM Funcionario f", Funcionario.class).getResultList();
        ObservableList<Funcionario> data = FXCollections.observableArrayList(funcionarios);
        tableView.setItems(data);
    }

    @FXML
    private void listarASC() {
        List<Funcionario> funcionarios = em.createQuery("SELECT f FROM Funcionario f ORDER BY f.nome ASC", Funcionario.class).getResultList();
        ObservableList<Funcionario> data = FXCollections.observableArrayList(funcionarios);
        tableView.setItems(data);
    }

    @FXML
    private void agrupar() {
        List<Funcionario> funcionarios = em.createQuery("SELECT f FROM Funcionario f ORDER BY f.funcao", Funcionario.class).getResultList();
        ObservableList<Funcionario> data = FXCollections.observableArrayList(funcionarios);
        tableView.setItems(data);
    }

    @FXML
    private void agruparFuncao() {
        List<Funcionario> funcionarios = em.createQuery("SELECT f FROM Funcionario f", Funcionario.class).getResultList();
        Map<String, List<Funcionario>> agrupadosPorFuncao = funcionarios.stream().collect(Collectors.groupingBy(Funcionario::getFuncao));

        StringBuilder result = new StringBuilder();
        agrupadosPorFuncao.forEach((funcao, lista) -> {
            result.append("Função: ").append(funcao).append("\nNomes: ");
            lista.forEach(f -> result.append(f.getNome()).append(", "));
            if (!lista.isEmpty()) {
                result.setLength(result.length() - 2);
            }
            result.append("\n\n");
        });

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Funcionários Agrupados por Função");
        alert.setHeaderText(null);
        alert.setContentText(result.toString());
        alert.showAndWait();
    }

    @FXML
    private void aumentoSalario() {
        em.getTransaction().begin();
        List<Funcionario> funcionarios = em.createQuery("SELECT f FROM Funcionario f", Funcionario.class).getResultList();
        for (Funcionario f : funcionarios) {
            BigDecimal novoSalario = f.getSalario().multiply(new BigDecimal("1.10").setScale(2, RoundingMode.HALF_UP));
            f.setSalario(novoSalario);
            em.merge(f);
        }
        em.getTransaction().commit();
        atualizarTabela();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aumento de salários");
        alert.setHeaderText(null);
        alert.setContentText("Salários alterados com sucesso!");
        alert.showAndWait();
    }

    @FXML
    private void aniversariantes() {
        List<Funcionario> funcionarios = em.createQuery("SELECT f FROM Funcionario f WHERE MONTH(f.dataNascimento) = 10 OR MONTH(f.dataNascimento) = 12", Funcionario.class).getResultList();
        ObservableList<Funcionario> data = FXCollections.observableArrayList(funcionarios);
        tableView.setItems(data);
    }

    @FXML
    private void maisVelho() {
        List<Funcionario> funcionarios = em.createQuery("SELECT f FROM Funcionario f ORDER BY f.dataNascimento ASC", Funcionario.class).setMaxResults(1).getResultList();

        if (!funcionarios.isEmpty()) {
            Funcionario maisVelho = funcionarios.getFirst();
            int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Funcionário Mais Velho");
            alert.setHeaderText(null);
            alert.setContentText("Nome: " + maisVelho.getNome() + ", Idade: " + idade + " anos");
            alert.showAndWait();
        }
    }

    @FXML
    private void totalSalario() {
        BigDecimal totalSalarios = em.createQuery("SELECT SUM(f.salario) FROM Funcionario f", BigDecimal.class).getSingleResult();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Total dos Salários");
        alert.setHeaderText(null);
        alert.setContentText("Total dos Salários: R$" + String.format("%,.2f", totalSalarios));
        alert.showAndWait();
    }

    @FXML
    private void qntdSalarios() {
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        List<Funcionario> funcionarios = em.createQuery("SELECT f FROM Funcionario f", Funcionario.class).getResultList();
        StringBuilder result = new StringBuilder();

        funcionarios.forEach(f -> {
            BigDecimal qntdSalarios = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            result.append(f.getNome()).append(" ganha ").append(qntdSalarios).append(" salário(s) mínimo(s).\n");
        });

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quantidade de Salários Mínimos");
        alert.setHeaderText(null);
        alert.setContentText(result.toString());
        alert.showAndWait();
    }

    @FXML
    private void atualizarTabela() {
        tableView.getItems().clear();
        listarTudo();
    }

}
