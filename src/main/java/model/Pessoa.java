package model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
public class Pessoa implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String nome;

    @Column(name="data_nasc")
    private LocalDate dataNascimento;

    public Pessoa() {
    }

    public Pessoa(Integer id, String nome, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

}