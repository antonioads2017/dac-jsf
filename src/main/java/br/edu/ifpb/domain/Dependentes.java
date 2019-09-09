package br.edu.ifpb.domain;

import java.util.List;

public interface Dependentes {

    void addDep(Dependente dependente);
    List<Dependente> todosOsDepentendes();
    Dependente localizarDependenteComId(String uuid);

    void excluir(Dependente dependente);
    void atualizar(Dependente dependente);
}
