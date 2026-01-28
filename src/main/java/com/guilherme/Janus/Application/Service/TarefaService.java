package com.guilherme.Janus.Application.Service;

import com.guilherme.Janus.Application.DTOs.Tarefa.CriarTarefaDto;
import com.guilherme.Janus.Application.Mapping.EntityMapper;
import com.guilherme.Janus.Domain.Entities.Categoria;
import com.guilherme.Janus.Domain.Entities.Tarefa;
import com.guilherme.Janus.Domain.Entities.Usuario;
import com.guilherme.Janus.Domain.enums.Prioridade;
import com.guilherme.Janus.Domain.enums.Status;
import com.guilherme.Janus.Data.Repositories.CategoriaRepository;
import com.guilherme.Janus.Data.Repositories.TarefaRepository;
import com.guilherme.Janus.Data.Repositories.UsuarioRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import static com.guilherme.Janus.Domain.enums.Status.ATRASADO;
import static com.guilherme.Janus.Domain.enums.Status.CONCLUIDO;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;
    private final CategoriaRepository categoriaTarefaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EntityMapper mapping;

    public TarefaService(TarefaRepository tarefaRepository, CategoriaRepository categoriaTarefaRepository, UsuarioRepository usuarioRepository, EntityMapper mapping) {
        this.tarefaRepository = tarefaRepository;
        this.categoriaTarefaRepository = categoriaTarefaRepository;
        this.usuarioRepository = usuarioRepository;
        this.mapping = mapping;
    }

    // criar tarefa nova
    public Tarefa salvarTarefa(String email, CriarTarefaDto dto){

        Tarefa tarefa = mapping.toEntity(dto);

        if(dto.getDt_ini() != null && dto.getDt_fim() != null && dto.getDt_ini().isAfter(dto.getDt_fim())){
            throw new RuntimeException("Data de inicio não pode ser maior que a data de fim");
        }


        if (dto.getCategoriaId() != null) {
            Categoria categoriaId = categoriaTarefaRepository.findById(dto.getCategoriaId())
                    .orElseThrow();
            tarefa.setCategoria(categoriaId);
        } else{
            tarefa.setCategoria(null);
        }

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        tarefa.setUsuario(usuario);

        return tarefaRepository.save(tarefa);
    
    }

    // pra facilitar o teste no postman
    public List<Tarefa> salvarVariasTarefas(List<CriarTarefaDto> dtos, String email) {
        List<Tarefa> tarefas = new ArrayList<>();

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        for (CriarTarefaDto dto : dtos) {
            Tarefa tarefa = mapping.toEntity(dto);

            if (dto.getCategoriaId() != null) {
                Categoria categoria = categoriaTarefaRepository.findById(dto.getCategoriaId())
                        .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
                tarefa.setCategoria(categoria);
            } else {
                tarefa.setCategoria(null);
            }

            tarefa.setUsuario(usuario);
            tarefas.add(tarefa);
        }

        return tarefaRepository.saveAll(tarefas);
    }


    // lista todas as tarefas
    public List<Tarefa> listarTarefas(String email){
        return tarefaRepository.findByUsuarioEmail(email);
    }

    // listar as tarefas de uma categoria (usa o id)
    public List<Tarefa> listarTarefasCategoria(String email, UUID id){
        return tarefaRepository.findByCategoriaIdAndUsuarioEmail(id, email);
    }

    // editar tarefa (usa o id)
    public Tarefa atualizarTarefa(UUID id, Tarefa tarefaAtualizada, String email){

        Tarefa tarefaExistente = tarefaRepository.findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        tarefaExistente.setTitulo(tarefaAtualizada.getTitulo());
        tarefaExistente.setCategoria(tarefaAtualizada.getCategoria());
        tarefaExistente.setDesc(tarefaAtualizada.getDesc());
        tarefaExistente.setPrioridade(tarefaAtualizada.getPrioridade());
        tarefaExistente.setDt_ini(tarefaAtualizada.getDt_ini());
        tarefaExistente.setDt_fim(tarefaAtualizada.getDt_fim());

        return tarefaRepository.save(tarefaExistente);
    }

    // deleta uma tarefa (usa o id)
    public void deletarTarefa(UUID id, String email){
        Tarefa tarefa = tarefaRepository.findByIdAndUsuarioEmail(id, email)
                        .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        tarefaRepository.delete(tarefa);
    }

    // atualiza o status das tarefas pra atrasado (se a data de fim estiver antes da data de hoje)
    @Transactional
    public int atualizarStatusAtrasado(){
        LocalDate hoje = LocalDate.now();
        return tarefaRepository.atualizarStatusAtrasado(ATRASADO, hoje, Status.EM_ANDAMENTO);
    }

    // atualiza o status pra concluido
    public Tarefa atualizarStatusConcluido(UUID id, String email){

        Tarefa tarefaExistente = tarefaRepository.findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new RuntimeException("Tarefa não Encontrado"));

        if (!(tarefaExistente.getStatus() == CONCLUIDO)){
            tarefaExistente.setStatus(CONCLUIDO);
            return tarefaRepository.save(tarefaExistente);
        }

        return tarefaExistente;
    }

    // filtro de busca
    public List<Tarefa> filtroDeBusca(String email, Categoria categoriaTarefa, Prioridade prioridade, String tipoData, List<Status> statusSelec){

        LocalDate hoje = LocalDate.now();
        LocalDate dataInicio = null;
        LocalDate dataFim = null;

        if("dia".equalsIgnoreCase(tipoData)){
            dataInicio = hoje;
            dataFim = hoje;
        }
        else if ("semana".equalsIgnoreCase(tipoData)){
            dataInicio = hoje.with(DayOfWeek.MONDAY);
            dataFim = hoje.with(DayOfWeek.SUNDAY);
        }
        else if ("mes".equalsIgnoreCase(tipoData)){
            dataInicio = hoje.withDayOfMonth(1);
            dataFim = hoje.withDayOfMonth(hoje.lengthOfMonth());
        }

        return tarefaRepository.filtroDeBusca(email, prioridade,categoriaTarefa, dataInicio, dataFim,
                statusSelec == null || statusSelec.isEmpty() ? null : statusSelec);

    }

}
