package br.edu.ifpb.web;

import br.edu.ifpb.dao.PessoasEmJDBC;
import br.edu.ifpb.domain.Dependente;
import br.edu.ifpb.domain.Pessoa;
import br.edu.ifpb.domain.Pessoas;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@SessionScoped
@Named
public class ControladorDePesssoas implements Serializable {

    private Pessoa pessoa;

    @Inject
    private Pessoas pessoaDao;

    @PostConstruct
    public void init(){
        pessoa = new Pessoa();
    }

    public List<Pessoa> listarPessoas(){
        return pessoaDao.todas();
    }

    public String delPessoa(Pessoa pessoa1){
        pessoaDao.excluir(pessoa1);
        return "";
    }

    public String editarPessoa(Pessoa editara){
        setPessoa(editara);
        return "edit.xhtml?faces-redirect=true";
    }

    public List<Dependente> listarDeps(){
        return pessoaDao.todosOsDepentendes();
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Pessoas getPessoaDao() {
        return pessoaDao;
    }

    public void setPessoaDao(Pessoas pessoaDao) {
        this.pessoaDao = pessoaDao;
    }
}
