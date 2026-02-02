package com.guilherme.Janus.Data.Repositories;

import com.guilherme.Janus.Application.DTOs.Dash.CategoriaResumoDTO;
import com.guilherme.Janus.Domain.Entities.Tarefa;
import com.guilherme.Janus.Domain.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface DashRepository extends JpaRepository<Tarefa, UUID> {

    // contagem de todas as tarefas
    @Query("""
        SELECT COUNT(t)
        FROM Tarefa t
        WHERE t.usuario.email = :email
    """)
    long countTotal(@Param("email") String email);

    // contagem de  tarefas atrasadas
    @Query("""
        SELECT COUNT(t)
        FROM Tarefa t
        WHERE t.usuario.email = :email
          AND t.dt_fim < :hoje
          AND t.status <> :concluida
    """)
    long countAtrasadas(
            @Param("email") String email,
            @Param("hoje") LocalDate hoje,
            @Param("concluida") Status concluida
    );

    // contagem de tarefas para hj
    @Query("""
        SELECT COUNT(t)
        FROM Tarefa t
        WHERE t.usuario.email = :email
          AND t.dt_fim = :hoje
    """)
    long countParaHoje(
            @Param("email") String email,
            @Param("hoje") LocalDate hoje
    );

    // contagem de tarefas concluidas
    @Query("""
        SELECT COUNT(t)
        FROM Tarefa t
        WHERE t.usuario.email = :email
          AND t.status = :status
    """)
    long countConcluidas(
            @Param("email") String email,
            @Param("status") Status status
    );

    @Query("""
        SELECT new com.guilherme.Janus.Application.DTOs.Dash.CategoriaResumoDTO(
            c.id,
            c.nome,
            COUNT(t),
            SUM(CASE WHEN t.dt_fim < :hoje AND t.status <> :concluido THEN 1 ELSE 0 END),
            SUM(CASE WHEN t.dt_fim = :hoje THEN 1 ELSE 0 END),
            SUM(CASE WHEN t.status = :concluido THEN 1 ELSE 0 END)
        )
        FROM Tarefa t
        JOIN t.categoria c
        WHERE t.usuario.email = :email
        GROUP BY c.id, c.nome
    """)
    List<CategoriaResumoDTO> resumoPorCategoria(
            @Param("email") String email,
            @Param("hoje") LocalDate hoje,
            @Param("concluido") Status concluido
    );
}
