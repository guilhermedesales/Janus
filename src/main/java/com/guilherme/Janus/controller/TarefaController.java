package com.guilherme.Janus.controller;

import com.guilherme.Janus.dto.FiltroTarefaDto;
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

    // criar uma nova tarefa
    @PostMapping("/salvar")
    public Tarefa salvarTarefa(@RequestBody TarefaDto dto){
        return tarefaService.salvarTarefa(dto);
    }

    @PostMapping("/salvarVarios")
    public List<Tarefa> salvarVarios(@RequestBody List<TarefaDto> dto){
        return tarefaService.salvarVariasTarefas(dto);
    }

    // listar todas as tarefas
    @GetMapping("/listar")
    public List<Tarefa> listarTarefas(){
        return tarefaService.listarTarefas();
    }

    // listar tarefas de uma categoria
    @GetMapping("/listarTarefaCategoria/{id}")
    public List<Tarefa> listarTarefasCategoria(@PathVariable Long id){
        return tarefaService.listarTarefasCategoria(id);
    }

    // atualizar tarefa
    @PutMapping("/atualizar/{id}")
    public Tarefa atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada){
        return tarefaService.atualizarTarefa(id, tarefaAtualizada);
    }

    // deletar tarefa
    @DeleteMapping("/deletar/{id}")
    public void deletarTarefa(@PathVariable Long id){
        tarefaService.deletarTarefa(id);
    }

    // atualizar status pra concluido
    @PatchMapping("/concluir/{id}")
    public Tarefa atualizarStatusConcluido(@PathVariable Long id){
        return tarefaService.atualizarStatusConcluido(id);
    }

    // filtro de busca
    @PostMapping("/buscar")
    public List<Tarefa> filtroDeBusca(@RequestBody FiltroTarefaDto dto){
        return tarefaService.filtroDeBusca(
                dto.getCategoriaTarefa(),
                dto.getPrioridade(),
                dto.getTipoData(),
                dto.getStatusSelec()
        );
    }
}
