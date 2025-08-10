package com.guilherme.Janus.service;

import com.guilherme.Janus.model.CategoriaTarefa;
import com.guilherme.Janus.repository.CategoriaTarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaTarefaService {

    @Autowired
    private final CategoriaTarefaRepository categoriaTarefaRepository;

    public CategoriaTarefaService(CategoriaTarefaRepository categoriaTarefaRepository){
        this.categoriaTarefaRepository = categoriaTarefaRepository;
    }

    // cria uma categoria
    public CategoriaTarefa salvarCategoriaTarefa(CategoriaTarefa categoriaTarefa){
        return categoriaTarefaRepository.save(categoriaTarefa);
    }

    // mostra todas as categorias
    public List<CategoriaTarefa> listarCategoriasTarefa(){
        return categoriaTarefaRepository.findAll();
    }

    // atualiza uma categoria
    public CategoriaTarefa atualizarCategoriaTarefa(Long id, CategoriaTarefa categoriaTarefaAtualizado){
        CategoriaTarefa categoriaTarefaExistente = categoriaTarefaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não Encontrada"));

        categoriaTarefaExistente.setNome(categoriaTarefaAtualizado.getNome());
        categoriaTarefaExistente.setDesc(categoriaTarefaAtualizado.getDesc());

        return categoriaTarefaRepository.save(categoriaTarefaExistente);
    }

    //deleta uma categoria
    public void deletarCategoriaTarefa(Long id){
        if (!categoriaTarefaRepository.existsById(id)){
            throw new RuntimeException("Categoria não Encontrada");
        }
        categoriaTarefaRepository.deleteById(id);
    }

}
