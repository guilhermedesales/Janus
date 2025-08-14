package com.guilherme.Janus.repository;

import com.guilherme.Janus.model.CategoriaTarefa;
import com.guilherme.Janus.model.Tarefa;
import com.guilherme.Janus.model.enums.Prioridade;
import com.guilherme.Janus.model.enums.Status;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TarefaRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    TarefaRepository repository;

    @Test
    void deveListarTarefasPeloIdDaCategoria() {

        CategoriaTarefa categoria = new CategoriaTarefa();
        categoria.setNome("Trabalhos");
        em.persist(categoria);

        Tarefa tarefa1 = new Tarefa();
        tarefa1.setTitulo("Tarefa 1");
        tarefa1.setCategoria(categoria);
        em.persist(tarefa1);

        Tarefa tarefa2 = new Tarefa();
        tarefa2.setTitulo("Tarefa 2");
        tarefa2.setCategoria(categoria);
        em.persist(tarefa2);

        em.flush();

        List<Tarefa> tarefas = repository.findByCategoriaId(categoria.getId());

        //assertEquals(2, tarefas.size());
        assertTrue(tarefas.stream().anyMatch(t -> t.getTitulo().equals("Tarefa 1")));
    }


    @Test
    void deveListarTarefasDaBusca() {

        CategoriaTarefa categoria = new CategoriaTarefa();
        categoria.setNome("Provas");
        em.persist(categoria);

        Tarefa tarefa1 = new Tarefa();
        tarefa1.setTitulo("Tarefa 1");
        tarefa1.setCategoria(categoria);
        tarefa1.setPrioridade(Prioridade.BAIXA);
        tarefa1.setStatus(Status.ATRASADO);
        tarefa1.setDt_fim(LocalDate.parse("2025-08-14"));
        em.persist(tarefa1);

        Tarefa tarefa2 = new Tarefa();
        tarefa2.setTitulo("Tarefa 2");
        tarefa2.setCategoria(categoria);
        tarefa2.setPrioridade(Prioridade.ALTA);
        tarefa2.setStatus(Status.EM_ANDAMENTO);
        tarefa2.setDt_fim(LocalDate.parse("2025-08-16"));
        em.persist(tarefa2);

        em.flush();

        List<Tarefa> tarefas = repository.filtroDeBusca(
               Prioridade.BAIXA,
                categoria,
                null,
                null,
                null);

        assertEquals(1, tarefas.size(), "Deve retornar apenas 1 tarefa");
        assertEquals("Tarefa 1", tarefas.get(0).getTitulo());
    }

    @Test
    void deveAtualizarStatusParaAtrasado(){

        LocalDate hoje = LocalDate.now();

        Tarefa tarefa1 = new Tarefa();
        tarefa1.setTitulo("Tarefa 1");
        tarefa1.setDt_fim(LocalDate.parse("2025-08-16"));
        tarefa1.setStatus(Status.EM_ANDAMENTO);
        em.persist(tarefa1);

        Tarefa tarefa2 = new Tarefa();
        tarefa2.setTitulo("Tarefa 2");
        tarefa2.setDt_fim(LocalDate.parse("2025-08-13"));
        tarefa2.setStatus(Status.EM_ANDAMENTO);
        em.persist(tarefa2);

        em.flush();

        int atualizadas = repository.atualizarStatusAtrasado(
                Status.ATRASADO,
                hoje,
                Status.EM_ANDAMENTO
        );

        assertEquals(1, atualizadas, "Deve atualizar apenas 1 tarefa atrasada");

    }
}