package br.edu.ifpb.dao;

import br.edu.ifpb.domain.Dependente;
import br.edu.ifpb.domain.Dependentes;

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
public class DependentesEmJDBC implements Dependentes {

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
            while(resultSet.next()){
               return criarDep(resultSet);
            }
        }catch (SQLException ex) {
            Logger.getLogger(PessoasEmJDBC.class.getName()).log(Level.SEVERE,null,ex);
        }
        return null;
    }

    @Override
    public void excluir(Dependente dependente) {
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM dependente WHERE uuid=? "
            );
            statement.setString(1,dependente.getUuid());
            statement.execute();
        }catch (SQLException ex) {
            Logger.getLogger(PessoasEmJDBC.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    @Override
    public void atualizar(Dependente dependente) {
        try{
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE dependente SET nome=?, dataN=? WHERE uuid=?"
            );
            statement.setString(1,dependente.getNome());
            statement.setDate(2,Date.valueOf(dependente.getDataDeNascimento()));
            statement.setString(3,dependente.getUuid());
            statement.executeUpdate();
        }catch (SQLException ex) {
            Logger.getLogger(PessoasEmJDBC.class.getName()).log(Level.SEVERE,null,ex);
        }
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
