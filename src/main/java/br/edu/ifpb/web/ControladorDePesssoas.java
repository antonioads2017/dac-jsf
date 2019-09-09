package br.edu.ifpb.web;

import br.edu.ifpb.dao.PessoasEmJDBC;
import br.edu.ifpb.domain.Dependente;
import br.edu.ifpb.domain.Pessoa;
import br.edu.ifpb.domain.Pessoas;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named
public class ControladorDePesssoas implements Serializable {

    private Pessoa pessoa = new Pessoa();
    private List<Pessoa> pessoas;
    private String cpf;
    private List<Pessoa> pessoasComFiltro;


    @Inject
    private Pessoas pessoaDao;

    @PostConstruct
    public void init(){
        this.pessoas = pessoaDao.todas();
        this.pessoasComFiltro=pessoas;
    }

    public String salvar(){
        this.pessoaDao.nova(pessoa);
        System.out.println(pessoa);
        this.pessoa = new Pessoa();
        this.pessoas=pessoaDao.todas();
        return null;
    }

    public String delPessoa(Pessoa pessoa1){
        pessoaDao.excluir(pessoa1);
        pessoas = pessoaDao.todas();
        return null;
    }

    public String editarPessoa(Pessoa editara){
        this.pessoa = editara;
        pessoas=pessoaDao.todas();
        return null;
    }

    public String atualizar(){
        this.pessoaDao.atualizar(pessoa);
        System.out.println(pessoa);
        this.pessoa = new Pessoa();
        pessoas = pessoaDao.todas();
        return null;
    }

    public String buscar(){
        this.pessoasComFiltro=this.pessoaDao.listarPorFiltro(cpf);
        return null;
    }


    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }


    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Pessoa> getPessoasComFiltro() {
        return pessoasComFiltro;
    }

    public void setPessoasComFiltro(List<Pessoa> pessoasComFiltro) {
        this.pessoasComFiltro = pessoasComFiltro;
    }
}
