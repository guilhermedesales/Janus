package com.guilherme.Janus.Application.Service;

import com.guilherme.Janus.Domain.Entities.Categoria;
import com.guilherme.Janus.Domain.Entities.Usuario;
import com.guilherme.Janus.Application.DTOs.Categoria.CriarCategoriaDTO;
import com.guilherme.Janus.Data.Repositories.CategoriaRepository;

import org.springframework.stereotype.Service;

import com.guilherme.Janus.Application.Mapping.EntityMapper;

import java.util.List;
import java.util.UUID;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final UsuarioIdentityService usuarioIdentityService;
    private final EntityMapper mapping;

    public CategoriaService(CategoriaRepository categoriaRepository, UsuarioIdentityService usuarioIdentityService, EntityMapper mapping) {
        this.categoriaRepository = categoriaRepository;
        this.usuarioIdentityService = usuarioIdentityService;
        this.mapping = mapping;
    }

    // cria uma categoria
    public Categoria criarCategoria(String email, CriarCategoriaDTO dto){
        Usuario usuario = usuarioIdentityService.buscarPorEmailOuErro(email);

        Categoria categoria = mapping.toEntity(dto);
        categoria.setUsuario(usuario);

        return categoriaRepository.save(categoria);
    }

    // listar todas as categorias
    public List<Categoria> listarCategorias(String email){
        Usuario usuario = usuarioIdentityService.buscarPorEmailOuErro(email);
        return categoriaRepository.findByUsuario(usuario);
    }

    // buscar categoria por id
    public Categoria buscarCategoriaPorId(String email, UUID id){
        usuarioIdentityService.buscarPorEmailOuErro(email);
        return categoriaRepository.findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));
    }

    // atualiza uma categoria
    public Categoria atualizarCategoria(String email, UUID id, CriarCategoriaDTO dto){
        usuarioIdentityService.buscarPorEmailOuErro(email);

        Categoria categoriaExistente = categoriaRepository.findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        categoriaExistente.setNome(dto.getNome());
        categoriaExistente.setDesc(dto.getDesc());
        categoriaExistente.setIcon(dto.getIcon());
        categoriaExistente.setCor(dto.getIcon());

        return categoriaRepository.save(categoriaExistente);
    }

    //deleta uma categoria
    public void deletarCategoria(String email, UUID id){
        usuarioIdentityService.buscarPorEmailOuErro(email);
        Categoria categoria = categoriaRepository.findByIdAndUsuarioEmail(id, email)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        categoriaRepository.delete(categoria);
    }

}
