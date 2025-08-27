package com.guilherme.Janus.controller;

import com.guilherme.Janus.model.CategoriaTarefa;
import com.guilherme.Janus.service.CategoriaTarefaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias", description = "Endpoints para gerenciamento de categorias de tarefas")
public class CategoriaTarefaController {

    @Autowired
    private final CategoriaTarefaService categoriaTarefaService;

    public CategoriaTarefaController (CategoriaTarefaService categoriaTarefaService){
        this.categoriaTarefaService = categoriaTarefaService;
    }

    // cria uma categoria
    @PostMapping("/salvar")
    @Operation(summary = "Criar categoria", description = "Cria uma nova categoria de tarefa para o usuário autenticado")
    public CategoriaTarefa salvarCategoriaTarefa(@RequestBody CategoriaTarefa categoriaTarefa, Principal principal){
        return categoriaTarefaService.salvarCategoriaTarefa( principal.getName(), categoriaTarefa);
    }

    // lista todas as categoria
    @GetMapping("/listar")
    @Operation(summary = "Listar categorias", description = "Lista todas as categorias de tarefas do usuário autenticado")
    public List<CategoriaTarefa> listarCategoriasTarefa(Principal principal){
        return categoriaTarefaService.listarCategoriasTarefa(principal.getName());
    }

    // atualiza uma categoria
    @PutMapping("/atualizar")
    @Operation(summary = "Atualizar categoria", description = "Atualiza os dados de uma categoria existente")
    public CategoriaTarefa atualizarCategoriaTarefa(
            @Parameter(description = "ID da categoria") @RequestBody Long id,
            @RequestBody CategoriaTarefa categoriaTarefaAtualizada,
            Principal principal){
        return categoriaTarefaService.atualizarCategoriaTarefa(principal.getName(), id, categoriaTarefaAtualizada);
    }

    // deleta uma categoria
    @DeleteMapping("/deletar")
    @Operation(summary = "Deletar categoria", description = "Deleta uma categoria pelo ID")
    public void deletarCategoriaTarefa(
            @Parameter(description = "ID da categoria") @RequestBody Long id,
            Principal principal){
        categoriaTarefaService.deletarCategoriaTarefa(principal.getName(), id);
    }
}
