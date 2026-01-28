package com.guilherme.Janus.API.Controller;

import com.guilherme.Janus.Application.DTOs.Tarefa.FiltroTarefaDto;
import com.guilherme.Janus.Application.DTOs.Categoria.CategoriaDTO;
import com.guilherme.Janus.Application.DTOs.Tarefa.CriarTarefaDto;
import com.guilherme.Janus.Application.Service.TarefaService;
import com.guilherme.Janus.Domain.Entities.Categoria;
import com.guilherme.Janus.Domain.Entities.Tarefa;
import com.guilherme.Janus.Application.DTOs.Tarefa.TarefaDTO;
import com.guilherme.Janus.Application.Mapping.EntityMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tarefas")
@Tag(name = "Tarefas", description = "Endpoints para gerenciamento de tarefas")
public class TarefaController {

    //@Autowired
    private final TarefaService tarefaService;
    private final EntityMapper mapping;

    public TarefaController(TarefaService tarefaService, EntityMapper mapping){
        this.tarefaService = tarefaService;
        this.mapping = mapping;
    }

    // criar uma nova tarefa
    @PostMapping("/salvar")
    @Operation(summary = "Criar nova tarefa", description = "Cria uma nova tarefa para o usuário autenticado")
    public TarefaDTO salvarTarefa(@RequestBody CriarTarefaDto dto, Principal principal){
        Tarefa tarefa = tarefaService.salvarTarefa(principal.getName(), dto);
        return mapping.toTarefaDTO(tarefa);
    }

    @PostMapping("/salvarVarios")
    @Operation(summary = "Criar várias tarefas", description = "Cria múltiplas tarefas de uma vez")
    public List<Tarefa> salvarVarios(@RequestBody List<CriarTarefaDto> dto, String email){
        return tarefaService.salvarVariasTarefas(dto, email);
    }

    // listar todas as tarefas
    @GetMapping("/listar")
    @Operation(summary = "Listar tarefas", description = "Lista todas as tarefas do usuário autenticado")
    public List<TarefaDTO> listarTarefas(Principal principal){
        List<Tarefa> tarefas = tarefaService.listarTarefas(principal.getName());
        return mapping.toTarefaDTOList(tarefas);
    }

    // listar tarefas de uma categoria
    @GetMapping("/listarTarefaCategoria/{id}")
    @Operation(summary = "Listar tarefas por categoria", description = "Lista todas as tarefas de uma categoria específica")
    public List<Tarefa> listarTarefasCategoria(
            @Parameter(description = "ID da categoria") @PathVariable UUID id,
            Principal principal){
        return tarefaService.listarTarefasCategoria(principal.getName(), id);
    }

    // atualizar tarefa
    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar tarefa", description = "Atualiza os dados de uma tarefa existente")
    public Tarefa atualizarTarefa(
            @Parameter(description = "ID da tarefa") @PathVariable UUID id,
            @RequestBody Tarefa tarefaAtualizada,
            Principal principal){
        return tarefaService.atualizarTarefa(id, tarefaAtualizada, principal.getName());
    }

    // deletar tarefa
    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar tarefa", description = "Deleta uma tarefa pelo ID")
    public void deletarTarefa(@Parameter(description = "ID da tarefa") @PathVariable UUID id, Principal principal){
        tarefaService.deletarTarefa(id, principal.getName());
    }

    // atualizar status pra concluido
    @PatchMapping("/concluir/{id}")
    @Operation(summary = "Concluir tarefa", description = "Marca uma tarefa como concluída")
    public Tarefa atualizarStatusConcluido(@Parameter(description = "ID da tarefa") @PathVariable UUID id, Principal principal){
        return tarefaService.atualizarStatusConcluido(id, principal.getName());
    }

    // // filtro de busca
    // @GetMapping("/buscar")
    // @Operation(summary = "Filtrar tarefas", description = "Busca tarefas usando filtros como categoria, prioridade, tipo de data e status")
    // public List<Tarefa> filtroDeBusca(@RequestBody FiltroTarefaDto dto, Principal principal){
    //     return tarefaService.filtroDeBusca(
    //             principal.getName(),
    //             dto.getCategoriaTarefa(),
    //             dto.getPrioridade(),
    //             dto.getTipoData(),
    //             dto.getStatusSelec()
    //     );
    // }
}
