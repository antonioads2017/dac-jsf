package br.edu.ifpb.dao;

import br.edu.ifpb.domain.CPF;
import br.edu.ifpb.domain.Dependente;
import br.edu.ifpb.domain.Pessoa;
import br.edu.ifpb.domain.Pessoas;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PessoasEmJDBC implements Pessoas {

    @Resource(name="java:app/jdbc/pessoas")
    private DataSource dataSource;
    private Connection connection;


    @PostConstruct
    public void init(){
        try{
            this.connection = this.dataSource.getConnection();
        }catch (SQLException ex){
            Logger.getLogger(PessoasEmJDBC.class.getName()).log(Level.SEVERE,null,ex);
        }
    }



    @Override
    public void nova(Pessoa pessoa) {
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO pessoa (nome,cpf) VALUES (?,?)"
            );
            statement.setString(1,pessoa.getNome());
            statement.setString(2,pessoa.getCpf().formatado());
            statement.executeUpdate();
        }catch (SQLException ex) {
            Logger.getLogger(PessoasEmJDBC.class.getName()).log(Level.SEVERE,null,ex);
        }

    }

    @Override
    public List<Pessoa> todas() {
        try{
            List<Pessoa> pessoas = new ArrayList<>();
           ResultSet resultSet = connection.prepareStatement(
                    "SELECT * FROM pessoa"
            ).executeQuery();
           while (resultSet.next()){
               pessoas.add(criarPessoa(resultSet));
           }
           return pessoas;
        }catch (SQLException ex) {
            Logger.getLogger(PessoasEmJDBC.class.getName()).log(Level.SEVERE,null,ex);
            return Collections.emptyList();
        }
    }

    @Override
    public void excluir(Pessoa pessoa) {
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM pessoa WHERE id=? "
            );
            statement.setInt(1,pessoa.getId());
            statement.execute();
        }catch (SQLException ex) {
            Logger.getLogger(PessoasEmJDBC.class.getName()).log(Level.SEVERE,null,ex);
        }

    }

    @Override
    public void atualizar(Pessoa pessoa) {
            try{
                PreparedStatement statement = connection.prepareStatement(
                        "UPDATE pessoa SET nome=?, cpf=? WHERE id=?"
                );
                statement.setString(1,pessoa.getNome());
                statement.setString(2,pessoa.getCpf().formatado());
                statement.setInt(3,pessoa.getId());
                statement.execute();
            }catch (SQLException ex) {
                Logger.getLogger(PessoasEmJDBC.class.getName()).log(Level.SEVERE,null,ex);
            }
    }

    @Override
    public List<Dependente> todosOsDepentendes() {
        try{
            List<Dependente> dependentes = new ArrayList<>();
            ResultSet resultSet = connection.prepareStatement(
                    "SELECT * FROM dependente"
            ).executeQuery();
            while (resultSet.next()){
                dependentes.add(criarDep(resultSet));
            }
            return dependentes;
        }catch (SQLException ex) {
            Logger.getLogger(PessoasEmJDBC.class.getName()).log(Level.SEVERE,null,ex);
            return Collections.emptyList();
        }
    }

    @Override
    public Dependente localizarDependenteComId(String uuid) {
        try{
           PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM dependente WHERE uuid=?"
            );
           statement.setString(1,uuid);
           ResultSet resultSet = statement.executeQuery();
           Dependente dependente = criarDep(resultSet);
           return dependente;
        }catch (SQLException ex) {
            Logger.getLogger(PessoasEmJDBC.class.getName()).log(Level.SEVERE,null,ex);
            return null;
        }
    }

    private Pessoa criarPessoa(ResultSet result) throws SQLException {
        String nome = result.getString("nome");
        String cpf = result.getString("cpf");
        int id = result.getInt("id");
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setId(id);
        pessoa.setCpf(new CPF(cpf));
        return pessoa;
    }
    private Dependente criarDep (ResultSet result) throws SQLException {
        String nome = result.getString("nome");
        LocalDate data = result.getDate("data").toLocalDate();
        String id = result.getString("uuid");
        Dependente dependente = new Dependente();
        dependente.setNome(nome);
        dependente.setUuid(id);
        dependente.setDataDeNascimento(data);
        return dependente;
    }
}
