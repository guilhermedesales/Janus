package com.guilherme.Janus.repository;

import com.guilherme.Janus.model.Tarefa;
import com.guilherme.Janus.model.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa>findByCategoriaId(Long categoriaId); // findBy (palavra chave do Jpa), categoriaId (junção do categoria q vem do Tarefa e o Id q vem do categoria)

    @Modifying
    @Transactional
    @Query("Update Tarefa set status = :novoStatus where dt_fim < :hoje && status = :statusAtual")
    int atualizarStatusAtrasado(@Param("novoStatus") Status novoStatus,
                                @Param("hoje") LocalDate hoje,
                                @Param("statusAtual") Status statusAtual);

}
