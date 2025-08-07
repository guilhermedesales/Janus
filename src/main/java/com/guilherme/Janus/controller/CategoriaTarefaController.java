package com.guilherme.Janus.controller;

import com.guilherme.Janus.model.CategoriaTarefa;
import com.guilherme.Janus.service.CategoriaTarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaTarefaController {

    @Autowired
    private final CategoriaTarefaService categoriaTarefaService;

    public CategoriaTarefaController (CategoriaTarefaService categoriaTarefaService){
        this.categoriaTarefaService = categoriaTarefaService;
    }

    @PostMapping("/salvar")
    public CategoriaTarefa salvarCategoriaTarefa(@RequestBody CategoriaTarefa categoriaTarefa){
        return categoriaTarefaService.salvarCategoriaTarefa(categoriaTarefa);
    }

    @GetMapping("/atualizar")
    public List<CategoriaTarefa> listarCategoriasTarefa(){
        return categoriaTarefaService.listarCategoriasTarefa();
    }

    @PutMapping("/atualizar")
    public CategoriaTarefa atualizarCategoriaTarefa(@RequestBody Long id, CategoriaTarefa categoriaTarefaAtualizada){
        return categoriaTarefaService.atualizarCategoriaTarefa(id, categoriaTarefaAtualizada);
    }

    @DeleteMapping("/deletar")
    public void deletarCategoriaTarefa(@RequestBody Long id){
        categoriaTarefaService.deletarCategoriaTarefa(id);
    }

}
