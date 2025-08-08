package com.guilherme.Janus.repository;

import com.guilherme.Janus.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa>findByCategoriaId(Long categoriaId);

}
