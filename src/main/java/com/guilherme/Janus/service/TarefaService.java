package com.guilherme.Janus.service;

import com.guilherme.Janus.dto.TarefaDto;
import com.guilherme.Janus.model.CategoriaTarefa;
import com.guilherme.Janus.model.Tarefa;
import com.guilherme.Janus.repository.CategoriaTarefaRepository;
import com.guilherme.Janus.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private final TarefaRepository tarefaRepository;

    private final CategoriaTarefaRepository categoriaTarefaRepository;


    public TarefaService(TarefaRepository tarefaRepository, CategoriaTarefaRepository categoriaTarefaRepository){
        this.tarefaRepository = tarefaRepository;
        this.categoriaTarefaRepository = categoriaTarefaRepository;
    }

    public Tarefa salvarTarefa(TarefaDto dto){

        Tarefa tarefa= new Tarefa();

        tarefa.setTitulo(dto.getTitulo());
        tarefa.setDesc(dto.getDesc());
        tarefa.setStatus(dto.getStatus());
        tarefa.setPrioridade(dto.getPrioridade());
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

    public List<Tarefa> listarTarefas(){
        return tarefaRepository.findAll();
    }

    public List<Tarefa> listarTarefasCategoria(Long id){
        return tarefaRepository.findByCategoriaId(id);
    }

    public Tarefa atualizarTarefa(Long id, Tarefa tarefaAtualizada){

        Tarefa tarefaExistente = tarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        tarefaExistente.setTitulo(tarefaAtualizada.getTitulo());
        tarefaExistente.setCategoria(tarefaAtualizada.getCategoria());
        tarefaExistente.setDesc(tarefaAtualizada.getDesc());
        tarefaExistente.setPrioridade(tarefaAtualizada.getPrioridade());
        tarefaExistente.setStatus(tarefaAtualizada.getStatus());
        tarefaExistente.setDt_ini(tarefaAtualizada.getDt_ini());
        tarefaExistente.setDt_fim(tarefaAtualizada.getDt_fim());

        return tarefaRepository.save(tarefaExistente);
    }

    public void deletarTarefa(Long id){
        if (!tarefaRepository.existsById(id)){
            throw new RuntimeException("Tarefa não encontrada");
        }
        tarefaRepository.deleteById(id);
    }

}
