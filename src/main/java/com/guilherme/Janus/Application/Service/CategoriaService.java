package com.guilherme.Janus.Application.Service;

import com.guilherme.Janus.Domain.Entities.Categoria;
import com.guilherme.Janus.Domain.Entities.Usuario;
import com.guilherme.Janus.Application.DTOs.Categoria.CriarCategoriaDTO;
import com.guilherme.Janus.Data.Repositories.CategoriaRepository;
import com.guilherme.Janus.Data.Repositories.UsuarioRepository;

import org.springframework.stereotype.Service;

import com.guilherme.Janus.Application.Mapping.EntityMapper;

import java.util.List;
import java.util.UUID;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EntityMapper mapping;

    public CategoriaService(CategoriaRepository categoriaRepository, UsuarioRepository usuarioRepository, EntityMapper mapping) {
        this.categoriaRepository = categoriaRepository;
        this.usuarioRepository = usuarioRepository;
        this.mapping = mapping;
    }

    // cria uma categoria
    public Categoria criarCategoria(String email, CriarCategoriaDTO dto){
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        Categoria categoria = mapping.toEntity(dto);
        categoria.setUsuario(usuario);

        return categoriaRepository.save(categoria);
    }

    // listar todas as categorias
    public List<Categoria> listarCategorias(String email){
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        return categoriaRepository.findByUsuario(usuario);
    }

    // buscar categoria por id
    public Categoria buscarCategoriaPorId(String email, UUID id){
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        return categoriaRepository.findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    // atualiza uma categoria
    public Categoria atualizarCategoria(String email, UUID id, Categoria categoriaAtualizado){

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        Categoria categoriaExistente = categoriaRepository.findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        categoriaExistente.setNome(categoriaAtualizado.getNome());
        categoriaExistente.setDesc(categoriaAtualizado.getDesc());

        return categoriaRepository.save(categoriaExistente);
    }

    //deleta uma categoria
    public void deletarCategoria(String email, UUID id){
        Categoria categoria = categoriaRepository.findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        categoriaRepository.delete(categoria);
    }

}
