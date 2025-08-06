package com.guilherme.Janus.service;

import com.guilherme.Janus.model.Tarefa;
import com.guilherme.Janus.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository){
        this.tarefaRepository = tarefaRepository;
    }

    public Tarefa salvarTarefa(Tarefa tarefa){
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> listarTarefas(){
        return tarefaRepository.findAll();
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
