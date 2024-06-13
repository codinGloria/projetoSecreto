package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "funcionario")
public class Funcionario extends Pessoa {

    @Column
    private BigDecimal salario;
    @Column
    private String funcao;

    public Funcionario() {
    }

    public Funcionario(Integer id, String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(id, nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }
}