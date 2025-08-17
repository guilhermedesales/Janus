package com.guilherme.Janus.service;

import com.guilherme.Janus.model.CategoriaTarefa;
import com.guilherme.Janus.model.Usuario;
import com.guilherme.Janus.repository.CategoriaTarefaRepository;
import com.guilherme.Janus.repository.TarefaRepository;
import com.guilherme.Janus.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaTarefaService {

    @Autowired
    private final CategoriaTarefaRepository categoriaTarefaRepository;
    @Autowired
    private TarefaRepository tarefaRepository;
    @Autowired
    private UsuarioRepository  usuarioRepository;

    public CategoriaTarefaService(CategoriaTarefaRepository categoriaTarefaRepository, UsuarioRepository usuarioRepository) {
        this.categoriaTarefaRepository = categoriaTarefaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // cria uma categoria
    public CategoriaTarefa salvarCategoriaTarefa(String email, CategoriaTarefa categoriaTarefa){
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario nã encontrado"));

        categoriaTarefa.setUsuario(usuario);

        return categoriaTarefaRepository.save(categoriaTarefa);
    }

    // mostra todas as categorias
    public List<CategoriaTarefa> listarCategoriasTarefa(String email){
        return categoriaTarefaRepository.findByUsuarioEmail(email);
    }

    // atualiza uma categoria
    public CategoriaTarefa atualizarCategoriaTarefa(String email, Long id, CategoriaTarefa categoriaTarefaAtualizado){

        CategoriaTarefa categoriaTarefaExistente = categoriaTarefaRepository.findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new RuntimeException("Categoria não Encontrada"));

        categoriaTarefaExistente.setNome(categoriaTarefaAtualizado.getNome());
        categoriaTarefaExistente.setDesc(categoriaTarefaAtualizado.getDesc());

        return categoriaTarefaRepository.save(categoriaTarefaExistente);
    }

    //deleta uma categoria
    public void deletarCategoriaTarefa(String email, Long id){

        CategoriaTarefa categoria = categoriaTarefaRepository.findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        categoriaTarefaRepository.delete(categoria);
    }

}
