package br.edu.ifpb.dao;

import br.edu.ifpb.domain.Dependente;
import br.edu.ifpb.domain.Pessoa;
import br.edu.ifpb.domain.Pessoas;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class PessoasEmJDBC implements Pessoas {

    @Resource(name="java:app/jdbc/pgadmin")
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
            statement.setString(2,pessoa.getCpf());
            statement.executeUpdate();
        }catch (SQLException ex) {
            Logger.getLogger(PessoasEmJDBC.class.getName()).log(Level.SEVERE,null,ex);
        }

    }

    @Override
    public List<Pessoa> todas() {
        List<Pessoa> pessoas = new ArrayList<>();
        try{
           PreparedStatement statement = connection.prepareStatement(
                   "SELECT * FROM pessoa ORDER BY nome"
           );
           ResultSet resultSet = statement.executeQuery();
           while (resultSet.next()){
               pessoas.add(criarPessoa(resultSet));
           }

        }catch (SQLException ex) {
            Logger.getLogger(PessoasEmJDBC.class.getName()).log(Level.SEVERE,null,ex);
        }
        return pessoas;
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
                statement.setString(2,pessoa.getCpf());
                statement.setInt(3,pessoa.getId());
                statement.execute();
            }catch (SQLException ex) {
                Logger.getLogger(PessoasEmJDBC.class.getName()).log(Level.SEVERE,null,ex);
            }
    }

    @Override
    public Pessoa localizarPorCPF(String cpf){
        try{
            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM pessoa WHERE cpf=?"
            );
            statement.setString(1,cpf);
            ResultSet resultSet = statement.executeQuery();
            Pessoa pessoa = criarPessoa(resultSet);
            return pessoa;
        }catch (SQLException ex) {
            Logger.getLogger(PessoasEmJDBC.class.getName()).log(Level.SEVERE,null,ex);
            return null;
        }
    }

    @Override
    public  void addDep(Dependente dependente){
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO dependente (uuid,nome,dataN) VALUES (?,?,?)"
            );
            statement.setString(1,dependente.getUuid());
            statement.setString(2,dependente.getNome());
            statement.setDate(3, Date.valueOf(dependente.getDataDeNascimento()));
            statement.executeUpdate();
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

    @Override
    public void setDepEmPessoa(int id, String uuid){
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE pessoa SET dependente_uuid=? WHERE id=?"
            );
            statement.setString(1,uuid);
            statement.setInt(2,id);
            statement.execute();
        }catch (SQLException ex) {
            Logger.getLogger(PessoasEmJDBC.class.getName()).log(Level.SEVERE,null,ex);
        }
    }

    private Pessoa criarPessoa(ResultSet result) throws SQLException {
        String nome = result.getString("nome");
        String cpf = result.getString("cpf");
        int id = result.getInt("id");
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome);
        pessoa.setId(id);
        pessoa.setCpf(cpf);
        return pessoa;
    }
    private Dependente criarDep (ResultSet result) throws SQLException {
        String nome = result.getString("nome");
        LocalDate data = result.getDate("dataN").toLocalDate();
        String id = result.getString("uuid");
        Dependente dependente = new Dependente();
        dependente.setNome(nome);
        dependente.setUuid(id);
        dependente.setDataDeNascimento(data);
        return dependente;
    }
}
