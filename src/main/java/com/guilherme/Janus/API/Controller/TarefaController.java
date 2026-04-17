package com.guilherme.Janus.API.Controller;

import com.guilherme.Janus.Application.DTOs.Tarefa.CriarTarefaDto;
import com.guilherme.Janus.Application.DTOs.Tarefa.TarefaDTO;
import com.guilherme.Janus.Application.Mapping.EntityMapper;
import com.guilherme.Janus.Application.Service.TarefaService;
import com.guilherme.Janus.Application.Service.TokenClaimsService;
import com.guilherme.Janus.Application.Service.UsuarioIdentityService;
import com.guilherme.Janus.Domain.Entities.Tarefa;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tarefas")
@Tag(name = "Tarefas", description = "Endpoints para gerenciamento de tarefas")
public class TarefaController {

    private final TarefaService tarefaService;
    private final EntityMapper mapping;
    private final TokenClaimsService tokenClaimsService;
    private final UsuarioIdentityService usuarioIdentityService;

    public TarefaController(TarefaService tarefaService,
                            EntityMapper mapping,
                            TokenClaimsService tokenClaimsService,
                            UsuarioIdentityService usuarioIdentityService) {
        this.tarefaService = tarefaService;
        this.mapping = mapping;
        this.tokenClaimsService = tokenClaimsService;
        this.usuarioIdentityService = usuarioIdentityService;
    }

    // criar uma nova tarefa
    @PostMapping("/salvar")
    @PreAuthorize("hasAuthority('tarefa:create')")
    @Operation(summary = "Criar nova tarefa", description = "Cria uma nova tarefa para o usuário autenticado")
    public TarefaDTO salvarTarefa(@RequestBody CriarTarefaDto dto, Authentication authentication) {
        String email = syncUsuario(authentication);
        Tarefa tarefa = tarefaService.salvarTarefa(email, dto);
        return mapping.toTarefaDTO(tarefa);
    }

    @PostMapping("/salvarVarios")
    @PreAuthorize("hasAuthority('tarefa:create')")
    @Operation(summary = "Criar várias tarefas", description = "Cria múltiplas tarefas de uma vez")
    public List<Tarefa> salvarVarios(@RequestBody List<CriarTarefaDto> dto, Authentication authentication) {
        String email = syncUsuario(authentication);
        return tarefaService.salvarVariasTarefas(dto, email);
    }

    // listar todas as tarefas
    @GetMapping("/listar")
    @PreAuthorize("hasAuthority('tarefa:view')")
    @Operation(summary = "Listar tarefas", description = "Lista todas as tarefas do usuário autenticado")
    public List<TarefaDTO> listarTarefas(Authentication authentication) {
        String email = syncUsuario(authentication);
        List<Tarefa> tarefas = tarefaService.listarTarefas(email);
        return mapping.toTarefaDTOList(tarefas);
    }

    // listar tarefas de uma categoria
    @GetMapping("/listarTarefaCategoria/{id}")
    @PreAuthorize("hasAuthority('tarefa:view')")
    @Operation(summary = "Listar tarefas por categoria", description = "Lista todas as tarefas de uma categoria específica")
    public List<TarefaDTO> listarTarefasCategoria(
            @Parameter(description = "ID da categoria")
            @PathVariable UUID id,
            Authentication authentication) {
        String email = syncUsuario(authentication);
        List<Tarefa> tarefas = tarefaService.listarTarefasCategoria(email, id);
        return mapping.toTarefaDTOList(tarefas);
    }

    // atualizar tarefa
    @PutMapping("/atualizar/{id}")
    @PreAuthorize("hasAuthority('tarefa:update')")
    @Operation(summary = "Atualizar tarefa", description = "Atualiza os dados de uma tarefa existente")
    public CriarTarefaDto atualizarTarefa(
            @Parameter(description = "ID da tarefa")
            @PathVariable UUID id,
            @RequestBody CriarTarefaDto dto,
            Authentication authentication) {
        String email = syncUsuario(authentication);
        Tarefa tarefa = tarefaService.atualizarTarefa(id, dto, email);
        return mapping.toDto(tarefa);
    }

    // deletar tarefa
    @DeleteMapping("/deletar/{id}")
    @PreAuthorize("hasAuthority('tarefa:delete')")
    @Operation(summary = "Deletar tarefa", description = "Deleta uma tarefa pelo ID")
    public void deletarTarefa(@Parameter(description = "ID da tarefa") @PathVariable UUID id,
                              Authentication authentication) {
        String email = syncUsuario(authentication);
        tarefaService.deletarTarefa(id, email);
    }

    // atualizar status pra concluido
    @PatchMapping("/concluir/{id}")
    @PreAuthorize("hasAuthority('tarefa:update')")
    @Operation(summary = "Concluir tarefa", description = "Marca uma tarefa como concluída")
    public TarefaDTO atualizarStatusConcluido(
            @Parameter(description = "ID da tarefa")
            @PathVariable UUID id,
            Authentication authentication) {
        String email = syncUsuario(authentication);
        Tarefa tarefa = tarefaService.atualizarStatusConcluido(id, email);
        return mapping.toTarefaDTO(tarefa);
    }

    private String syncUsuario(Authentication authentication) {
        UUID authUserId = tokenClaimsService.getAuthUserId(authentication);
        String email = tokenClaimsService.getEmail(authentication);
        String nome = tokenClaimsService.getNome(authentication);
        usuarioIdentityService.obterOuCriarUsuario(authUserId, email, nome);
        return email;
    }
}
