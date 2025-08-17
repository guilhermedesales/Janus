package com.guilherme.Janus.controller;

import com.guilherme.Janus.model.CategoriaTarefa;
import com.guilherme.Janus.service.CategoriaTarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaTarefaController {

    @Autowired
    private final CategoriaTarefaService categoriaTarefaService;

    public CategoriaTarefaController (CategoriaTarefaService categoriaTarefaService){
        this.categoriaTarefaService = categoriaTarefaService;
    }

    // cria uma categoria
    @PostMapping("/salvar")
    public CategoriaTarefa salvarCategoriaTarefa(@RequestBody CategoriaTarefa categoriaTarefa, Principal principal){
        return categoriaTarefaService.salvarCategoriaTarefa( principal.getName(), categoriaTarefa);
    }

    // lista todas as categoria
    @GetMapping("/listar")
    public List<CategoriaTarefa> listarCategoriasTarefa(Principal principal){
        return categoriaTarefaService.listarCategoriasTarefa(principal.getName());
    }

    // atualiza uma categoria
    @PutMapping("/atualizar")
    public CategoriaTarefa atualizarCategoriaTarefa(@RequestBody Long id, CategoriaTarefa categoriaTarefaAtualizada, Principal principal){
        return categoriaTarefaService.atualizarCategoriaTarefa(principal.getName(), id, categoriaTarefaAtualizada);
    }

    // deleta uma categoria
    @DeleteMapping("/deletar")
    public void deletarCategoriaTarefa(@RequestBody Long id, Principal principal){
        categoriaTarefaService.deletarCategoriaTarefa(principal.getName(), id);
    }

}
