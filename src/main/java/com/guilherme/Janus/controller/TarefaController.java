package com.guilherme.Janus.controller;

import com.guilherme.Janus.dto.TarefaDto;
import com.guilherme.Janus.model.Tarefa;
import com.guilherme.Janus.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService){
        this.tarefaService = tarefaService;
    }

    @PostMapping("/salvar")
    public Tarefa salvarTarefa(@RequestBody TarefaDto dto){
        return tarefaService.salvarTarefa(dto);
    }

    @GetMapping("/listar")
    public List<Tarefa> listarTarefas(){
        return tarefaService.listarTarefas();
    }

    @PutMapping("/atualizar/{id}")
    public Tarefa atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada){
        return tarefaService.atualizarTarefa(id, tarefaAtualizada);
    }

    @DeleteMapping("/deletar/{id}")
    public void deletarTarefa(@PathVariable Long id){
        tarefaService.deletarTarefa(id);
    }

}
