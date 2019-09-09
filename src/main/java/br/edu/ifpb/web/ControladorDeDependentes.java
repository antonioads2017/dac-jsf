package br.edu.ifpb.web;

import br.edu.ifpb.domain.Dependente;
import br.edu.ifpb.domain.Dependentes;
import br.edu.ifpb.domain.Pessoa;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@ViewScoped
@Named
public class ControladorDeDependentes implements Serializable {

    private Dependente dependente = new Dependente();
    private List<Dependente> dependentes;

    @Inject
    private Dependentes dependenteDao;

    @PostConstruct
    public void init(){
        this.dependentes = dependenteDao.todosOsDepentendes();
    }

    public String salvar(){
        this.dependente.setUuid(UUID.randomUUID().toString());
        this.dependenteDao.addDep(dependente);
        this.dependente = new Dependente();
        this.dependentes=dependenteDao.todosOsDepentendes();
        return null;
    }

    public String delDep(Dependente dependente){
        dependenteDao.excluir(dependente);
        dependentes = dependenteDao.todosOsDepentendes();;
        return null;
    }

    public String atualizar(){
        this.dependenteDao.atualizar(dependente);
        this.dependente =new Dependente();
        dependentes = dependenteDao.todosOsDepentendes();
        return null;
    }

    public String editarDep(Dependente editara){
        this.dependente = editara;
        dependentes=dependenteDao.todosOsDepentendes();
        return null;
    }
    public Dependente getDependente() {
        return dependente;
    }

    public void setDependente(Dependente dependente) {
        this.dependente = dependente;
    }

    public List<Dependente> getDependentes() {
        return dependentes;
    }

    public void setDependentes(List<Dependente> dependentes) {
        this.dependentes = dependentes;
    }

    public Dependentes getDependenteDao() {
        return dependenteDao;
    }

    public void setDependenteDao(Dependentes dependenteDao) {
        this.dependenteDao = dependenteDao;
    }
}
