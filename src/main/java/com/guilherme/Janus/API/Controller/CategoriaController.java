package com.guilherme.Janus.API.Controller;

import com.guilherme.Janus.Application.DTOs.Categoria.CategoriaDTO;
import com.guilherme.Janus.Application.DTOs.Categoria.CriarCategoriaDTO;
import com.guilherme.Janus.Application.DTOs.Categoria.CategoriaSimplesDTO;
import com.guilherme.Janus.Application.Service.CategoriaService;
import com.guilherme.Janus.Domain.Entities.Categoria;
import com.guilherme.Janus.Application.Mapping.EntityMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/categorias")
@Tag(name = "Categorias", description = "Endpoints para gerenciamento de categorias de tarefas")
public class CategoriaController {


    private final CategoriaService categoriaService;
    private final EntityMapper mapping;

    public CategoriaController (CategoriaService categoriaService, EntityMapper mapping){
        this.categoriaService = categoriaService;
        this.mapping = mapping;
    }

    // cria uma categoria
    @PostMapping("/criar")
    @Operation(summary = "Criar categoria", description = "Cria uma nova categoria de tarefa para o usuário autenticado")
    public CriarCategoriaDTO criarCategoria(@RequestBody CriarCategoriaDTO dto, Principal principal){

        Categoria categoria = categoriaService.criarCategoria( principal.getName(), dto);
        return mapping.toDto(categoria);
    }

    // lista todas as categoria (sem mostrar tarefas)
    @GetMapping("/listar")
    @Operation(summary = "Listar categorias", description = "Lista todas as categorias de tarefas do usuário autenticado")
    public List<CategoriaSimplesDTO> listarCategorias(Principal principal){
        List<Categoria> categorias = categoriaService.listarCategorias(principal.getName());
        return mapping.toCategoriaSimplesDTOList(categorias);
    }

    @GetMapping("/buscar/{id}")
    @Operation(summary = "Buscar categoria por ID", description = "Busca uma categoria específica pelo ID")
    public CategoriaDTO buscarCategoriaPorId(
            @Parameter(description = "ID da categoria")
            @PathVariable UUID id, Principal principal){
        Categoria categoria = categoriaService.buscarCategoriaPorId(principal.getName(), id);
        return mapping.toCategoriaDTO(categoria);
    }

    // atualiza uma categoria
    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar categoria", description = "Atualiza os dados de uma categoria existente")
    public CriarCategoriaDTO atualizarCategoria(
            @Parameter(description = "ID da categoria")
            @PathVariable UUID id,
            @RequestBody CriarCategoriaDTO dto, Principal principal){
        Categoria categoria = categoriaService.atualizarCategoria(principal.getName(), id, dto);
        return mapping.toDto(categoria);
    }

    // deleta uma categoria
    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar categoria", description = "Deleta uma categoria pelo ID")
    public void deletarCategoria(
            @Parameter(description = "ID da categoria")
            @PathVariable UUID id,
            Principal principal){
        categoriaService.deletarCategoria(principal.getName(), id);
    }
}
