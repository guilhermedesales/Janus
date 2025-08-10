package com.guilherme.Janus.service;

import com.guilherme.Janus.dto.TarefaDto;
import com.guilherme.Janus.model.CategoriaTarefa;
import com.guilherme.Janus.model.Tarefa;
import com.guilherme.Janus.model.enums.Status;
import com.guilherme.Janus.repository.CategoriaTarefaRepository;
import com.guilherme.Janus.repository.TarefaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static com.guilherme.Janus.model.enums.Status.ATRASADO;
import static com.guilherme.Janus.model.enums.Status.CONCLUIDO;

@Service
public class TarefaService {

    @Autowired
    private final TarefaRepository tarefaRepository;

    private final CategoriaTarefaRepository categoriaTarefaRepository;


    public TarefaService(TarefaRepository tarefaRepository, CategoriaTarefaRepository categoriaTarefaRepository){
        this.tarefaRepository = tarefaRepository;
        this.categoriaTarefaRepository = categoriaTarefaRepository;
    }

    // criar tarefa nova
    public Tarefa salvarTarefa(TarefaDto dto){

        Tarefa tarefa= new Tarefa();

        tarefa.setTitulo(dto.getTitulo());
        tarefa.setDesc(dto.getDesc());
        tarefa.setPrioridade(dto.getPrioridade());
        tarefa.setStatus(Status.EM_ANDAMENTO);
        tarefa.setDt_ini(dto.getDt_ini());
        tarefa.setDt_fim(dto.getDt_fim());

        if (dto.getCategoriaId() != null) {
            CategoriaTarefa categoriaId = categoriaTarefaRepository.findById(dto.getCategoriaId())
                    .orElseThrow();
            tarefa.setCategoria(categoriaId);
        } else{
            tarefa.setCategoria(null);
        }

        return tarefaRepository.save(tarefa);
    }

    // lista todas as tarefas
    public List<Tarefa> listarTarefas(){
        return tarefaRepository.findAll();
    }

    // listar as tarefas de uma categoria (usa o id)
    public List<Tarefa> listarTarefasCategoria(Long id){
        return tarefaRepository.findByCategoriaId(id);
    }

    // editar tarefa (usa o id)
    public Tarefa atualizarTarefa(Long id, Tarefa tarefaAtualizada){

        Tarefa tarefaExistente = tarefaRepository.findById(id)
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
    public void deletarTarefa(Long id){
        if (!tarefaRepository.existsById(id)){
            throw new RuntimeException("Tarefa não encontrada");
        }
        tarefaRepository.deleteById(id);
    }

    // atualiza o status das tarefas pra atrasado (se a data de dim estiver antes da data de hoje)
    @Transactional
    public int atualizarStatusAtrasado(){
        LocalDate hoje = LocalDate.now();
        return tarefaRepository.atualizarStatusAtrasado(ATRASADO, hoje, Status.EM_ANDAMENTO);
    }

    public Tarefa atualizarStatusConcluido(Long id){

        Tarefa tarefaExistente = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não Encontrado"));

        if (!(tarefaExistente.getStatus() == CONCLUIDO)){
            tarefaExistente.setStatus(CONCLUIDO);
            return tarefaRepository.save(tarefaExistente);
        }

        return tarefaExistente;
    }

}
