package com.guilherme.Janus.repository;

import com.guilherme.Janus.model.CategoriaTarefa;
import com.guilherme.Janus.model.Tarefa;
import com.guilherme.Janus.model.enums.Prioridade;
import com.guilherme.Janus.model.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    // listar tarefas de uma categoria
    List<Tarefa>findByCategoriaId(Long categoriaId); // findBy (palavra chave do Jpa), categoriaId (junção do categoria q vem do Tarefa e o Id q vem do categoria)

    // atualiza o status de todas as tarefas atrasadas
    @Modifying
    @Transactional
    @Query("Update Tarefa t set t.status = :novoStatus where t.dt_fim < :hoje and t.status = :statusAtual")
    int atualizarStatusAtrasado(@Param("novoStatus") Status novoStatus,
                                @Param("hoje") LocalDate hoje,
                                @Param("statusAtual") Status statusAtual);


    // filtro de busca
    @Query("Select t from Tarefa t where " +
            "(:prioridade is Null or t.prioridade = :prioridade) and " +
            "(:categoria is null or t.categoria = :categoria) and " +
            "(:dataInicio is null or t.dt_fim >= :dataInicio) and " +
            "(:dataFim is null or t.dt_fim <= :dataFim) and " +
            "(:statusList is null or t.status in :statusList)")
    List<Tarefa> filtroDeBusca(
            @Param("prioridade")Prioridade prioridade,
            @Param("categoria")CategoriaTarefa categoriaTarefa,
            @Param("dataInicio")LocalDate dataInicio,
            @Param("dataFim")LocalDate dataFim,
            @Param("statusList") List<Status> statusList
    );

}
