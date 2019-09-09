package br.edu.ifpb.domain;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 26/04/2019, 18:38:44
 */
public class Dependente {

    private String uuid;
    private String nome;
    private LocalDate dataDeNascimento;

    public Dependente() {
    }

    public Dependente(String uuid,String nome,LocalDate dataDeNascimento) {
        this(nome,dataDeNascimento);
        this.uuid=uuid;
    }
    public Dependente(String nome, LocalDate dataDeNascimento) {
        this.nome = nome;
        this.dataDeNascimento = dataDeNascimento;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(LocalDate dataDeNascimento) {
        this.dataDeNascimento = dataDeNascimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dependente that = (Dependente) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(nome, that.nome) &&
                Objects.equals(dataDeNascimento, that.dataDeNascimento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, nome, dataDeNascimento);
    }

    @Override
    public String toString() {
        return "Dependente{" +
                "uuid='" + uuid + '\'' +
                ", nome='" + nome + '\'' +
                ", dataDeNascimento=" + dataDeNascimento +
                '}';
    }
}
