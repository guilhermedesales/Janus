package com.guilherme.Janus.service;

import com.guilherme.Janus.dto.TarefaDto;
import com.guilherme.Janus.model.CategoriaTarefa;
import com.guilherme.Janus.model.Tarefa;
import com.guilherme.Janus.model.Usuario;
import com.guilherme.Janus.model.enums.Prioridade;
import com.guilherme.Janus.model.enums.Status;
import com.guilherme.Janus.repository.TarefaRepository;
import com.guilherme.Janus.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TarefaServiceTest {

    @Mock
    private TarefaRepository repository;

    @Mock
    private UsuarioRepository  usuarioRepository;

    @InjectMocks
    private TarefaService service;

    @Test
    void deveSalvarTarefa() {

        Usuario usuario = new Usuario();
        usuario.setEmail("email.com");

        TarefaDto dto= new TarefaDto();
        dto.setTitulo("Nova Tarefa");

        Tarefa entidade = new Tarefa(); // simular entidade q o banco devolve
        entidade.setId(1L);
        entidade.setTitulo("Nova Tarefa");

        when(usuarioRepository.findByEmail(usuario.getEmail())).thenReturn(Optional.of(usuario));

        when(repository.save(any(Tarefa.class))).thenReturn(entidade);

        Tarefa result = service.salvarTarefa(usuario.getEmail(), dto);

        assertNotNull(result);
        assertEquals(entidade.getTitulo(), result.getTitulo());

        verify(repository, times(1)).save(any(Tarefa.class));
        verify(usuarioRepository, times(1)).findByEmail(usuario.getEmail());
    }

    @Test
    void deveListarTodasTarefas() {

        Usuario usuario = new Usuario();
        usuario.setEmail("email.com");

        Tarefa tarefa1 = new Tarefa();
        tarefa1.setTitulo("Tarefa 1");

        Tarefa tarefa2 = new Tarefa();
        tarefa2.setTitulo("Tarefa 2");

        List<Tarefa> entidades = Arrays.asList(tarefa1, tarefa2);

        when(repository.findByUsuarioEmail(usuario.getEmail())).thenReturn(entidades);

        List<Tarefa> result = service.listarTarefas(usuario.getEmail());

        assertEquals(2, result.size());
        assertEquals(entidades.get(0).getTitulo(), result.get(0).getTitulo());
        assertEquals(entidades.get(1).getTitulo(), result.get(1).getTitulo());
        verify(repository, times(1)).findByUsuarioEmail(usuario.getEmail());

    }

    @Test
    void deveListarTarefasDeUmaCategoria() {

        Usuario usuario = new Usuario();
        usuario.setEmail("email.com");

        CategoriaTarefa categoria = new CategoriaTarefa();
        categoria.setId(1L);

        Tarefa tarefa1 = new Tarefa();
        tarefa1.setTitulo("Tarefa 1");
        tarefa1.setCategoria(categoria);

        Tarefa tarefa2 = new Tarefa();
        tarefa2.setTitulo("Tarefa 2");
        tarefa2.setCategoria(categoria);

        List<Tarefa> entidades = Arrays.asList(tarefa1, tarefa2);

        when(repository.findByCategoriaIdAndUsuarioEmail(1L, usuario.getEmail()))
                .thenReturn(entidades);

        List<Tarefa> result = service.listarTarefasCategoria(usuario.getEmail(), 1L);

        assertEquals(2, entidades.size());
        assertEquals("Tarefa 1", entidades.get(0).getTitulo());
        assertEquals("Tarefa 2", entidades.get(1).getTitulo());
        verify(repository, times(1))
                .findByCategoriaIdAndUsuarioEmail(1L, usuario.getEmail());

    }

    @Test
    void atualizarTarefa() {

        Usuario usuario = new Usuario();
        usuario.setEmail("email.com");

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Nova Tarefa");

        when(repository.findByIdAndUsuarioEmail(1L, usuario.getEmail()))
                .thenReturn(Optional.of(tarefa));

        Tarefa tarefaAtualizada = new Tarefa();
        tarefaAtualizada.setTitulo("Tarefa Atualizada");

        when(repository.save(any(Tarefa.class))).thenReturn(tarefaAtualizada);

        Tarefa result = service.atualizarTarefa(1L, tarefaAtualizada, usuario.getEmail());

        assertEquals(tarefaAtualizada.getTitulo(), result.getTitulo());
        verify(repository, times(1))
                .findByIdAndUsuarioEmail(1L, usuario.getEmail());
        verify(repository, times(1)).save(any(Tarefa.class));

    }

    @Test
    void deveDeletarTarefa() {

        Usuario usuario = new Usuario();
        usuario.setEmail("email.com");

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("tarefa");
        tarefa.setId(1L);

        when(repository.findByIdAndUsuarioEmail(1L, usuario.getEmail()))
                .thenReturn(Optional.of(tarefa));

        service.deletarTarefa(1L, usuario.getEmail());

        verify(repository, times(1))
                .findByIdAndUsuarioEmail(1L, usuario.getEmail());
        verify(repository, times(1)).delete(tarefa);

    }

    @Test
    void DeveAtualizarStatusParaAtrasado() {

        LocalDate hoje = LocalDate.of(2025, 8, 17);

        when(repository.atualizarStatusAtrasado(Status.ATRASADO, hoje, Status.EM_ANDAMENTO)).thenReturn(1);

        int result = service.atualizarStatusAtrasado();

        assertEquals(1, result);

        verify(repository, times(1))
                .atualizarStatusAtrasado(Status.ATRASADO, hoje, Status.EM_ANDAMENTO);

        verify(repository, never())
                .atualizarStatusAtrasado(Status.ATRASADO, hoje, Status.CONCLUIDO);
    }

    @Test
    void deveAtualizarStatusParaConcluido() {

        Usuario usuario = new Usuario();
        usuario.setEmail("email.com");

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Tarefa 1");
        tarefa.setStatus(Status.EM_ANDAMENTO);
        tarefa.setId(1L);

        when(repository.findByIdAndUsuarioEmail(1L, usuario.getEmail()))
                .thenReturn(Optional.of(tarefa));

        service.atualizarStatusConcluido(tarefa.getId(), usuario.getEmail());

        verify(repository, times(1))
                .findByIdAndUsuarioEmail(1L, usuario.getEmail());
        verify(repository, times(1)).save(tarefa);
        assertEquals(Status.CONCLUIDO, tarefa.getStatus());

    }

    @Test
    void naoDeveAtualizarTarefaConcluidaParaConcluido() { // não altera status pra concluido de tarefas ja conscluidas

        Usuario usuario = new Usuario();
        usuario.setEmail("email.com");

        Tarefa tarefa = new Tarefa();
        tarefa.setTitulo("Tarefa 1");
        tarefa.setStatus(Status.CONCLUIDO);
        tarefa.setId(1L);

        when(repository.findByIdAndUsuarioEmail(1L, usuario.getEmail()))
                .thenReturn(Optional.of(tarefa));

        service.atualizarStatusConcluido(tarefa.getId(), usuario.getEmail());

        verify(repository, times(1))
                .findByIdAndUsuarioEmail(1L, usuario.getEmail());
        verify(repository, times(0)).save(tarefa);

    }

    @Test
    void deveListarTarefasDoFiltroDeDia() {

    Usuario usuario = new Usuario();
    usuario.setEmail("email.com");

    LocalDate hoje = LocalDate.now();

        List<Tarefa> tarefas = List.of(new Tarefa());
        when(repository.filtroDeBusca(
                usuario.getEmail(),
                Prioridade.MEDIA,
                null,
                hoje,
                hoje,
                null)
        ).thenReturn(tarefas);

    List<Tarefa> result = service.filtroDeBusca(usuario.getEmail(), null,Prioridade.MEDIA,"dia",null);

    verify(repository).filtroDeBusca(usuario.getEmail(), Prioridade.MEDIA,null, hoje, hoje, null);
    assertEquals(tarefas, result);

    }

    @Test
    void deveListarTarefasDoFiltroDeSemana() {

        Usuario usuario = new Usuario();
        usuario.setEmail("email.com");

        LocalDate hoje = LocalDate.now();
        LocalDate semanaIni = hoje.with(DayOfWeek.MONDAY);
        LocalDate semanaFim = hoje.with(DayOfWeek.SUNDAY);

        List<Tarefa> tarefas = List.of(new Tarefa());
        when(repository.filtroDeBusca(
                usuario.getEmail(),
                Prioridade.MEDIA,
                null,
                semanaIni,
                semanaFim,
                null)
        ).thenReturn(tarefas);

        List<Tarefa> result = service.filtroDeBusca(usuario.getEmail(), null,Prioridade.MEDIA,"semana",null);

        verify(repository).filtroDeBusca(usuario.getEmail(), Prioridade.MEDIA,null, semanaIni, semanaFim, null);
        assertEquals(tarefas, result);

    }

    @Test
    void deveListarTarefasDoFiltroDeMes() {

        Usuario usuario = new Usuario();
        usuario.setEmail("email.com");

        LocalDate hoje = LocalDate.now();
        LocalDate mesIni = hoje.withDayOfMonth(1);
        LocalDate mesFim = hoje.withDayOfMonth(hoje.lengthOfMonth());

        List<Tarefa> tarefas = List.of(new Tarefa());
        when(repository.filtroDeBusca(
                usuario.getEmail(),
                Prioridade.MEDIA,
                null,
                mesIni,
                mesFim,
                null)
        ).thenReturn(tarefas);

        List<Tarefa> result = service.filtroDeBusca(usuario.getEmail(), null,Prioridade.MEDIA,"mes",null);

        verify(repository).filtroDeBusca(usuario.getEmail(), Prioridade.MEDIA,null, mesIni, mesFim, null);
        assertEquals(tarefas, result);

    }
}