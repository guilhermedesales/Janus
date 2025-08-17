package com.guilherme.Janus.controller;

import com.guilherme.Janus.dto.FiltroTarefaDto;
import com.guilherme.Janus.dto.TarefaDto;
import com.guilherme.Janus.model.Tarefa;
import com.guilherme.Janus.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    public Tarefa salvarTarefa(@RequestBody TarefaDto dto, Principal principal){
        return tarefaService.salvarTarefa(principal.getName(), dto);
    }

    @PostMapping("/salvarVarios")
    public List<Tarefa> salvarVarios(@RequestBody List<TarefaDto> dto){
        return tarefaService.salvarVariasTarefas(dto);
    }

    // listar todas as tarefas
    @GetMapping("/listar")
    public List<Tarefa> listarTarefas(Principal principal){
        return tarefaService.listarTarefas(principal.getName());
    }

    // listar tarefas de uma categoria
    @GetMapping("/listarTarefaCategoria/{id}")
    public List<Tarefa> listarTarefasCategoria(@PathVariable Long id, Principal principal){
        return tarefaService.listarTarefasCategoria(principal.getName(), id);
    }

    // atualizar tarefa
    @PutMapping("/atualizar/{id}")
    public Tarefa atualizarTarefa(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada, Principal principal){
        return tarefaService.atualizarTarefa(id, tarefaAtualizada, principal.getName());
    }

    // deletar tarefa
    @DeleteMapping("/deletar/{id}")
    public void deletarTarefa(@PathVariable Long id, Principal principal){
        tarefaService.deletarTarefa(id, principal.getName());
    }

    // atualizar status pra concluido
    @PatchMapping("/concluir/{id}")
    public Tarefa atualizarStatusConcluido(@PathVariable Long id, Principal principal){
        return tarefaService.atualizarStatusConcluido(id, principal.getName());
    }

    // filtro de busca
    @PostMapping("/buscar")
    public List<Tarefa> filtroDeBusca(@RequestBody FiltroTarefaDto dto, Principal principal){
        return tarefaService.filtroDeBusca(
                principal.getName(),
                dto.getCategoriaTarefa(),
                dto.getPrioridade(),
                dto.getTipoData(),
                dto.getStatusSelec()
        );
    }
}
