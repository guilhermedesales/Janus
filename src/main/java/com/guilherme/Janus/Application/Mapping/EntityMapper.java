package com.guilherme.Janus.Application.Mapping;

import com.guilherme.Janus.Domain.Entities.Tarefa;
import com.guilherme.Janus.Application.DTOs.Categoria.CriarCategoriaDTO;
import com.guilherme.Janus.Application.DTOs.Tarefa.CriarTarefaDto;
import com.guilherme.Janus.Application.DTOs.Tarefa.TarefaDTO;
import com.guilherme.Janus.Domain.Entities.Categoria;

import org.mapstruct.Mapper;

import com.guilherme.Janus.Application.DTOs.Categoria.CategoriaDTO;
import com.guilherme.Janus.Application.DTOs.Categoria.CategoriaSimplesDTO;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EntityMapper {

    // tarefa map
    @org.mapstruct.Mapping(target = "categoria", ignore = true)
    @org.mapstruct.Mapping(target = "usuario", ignore = true)
    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "status", ignore = true)
    @org.mapstruct.Mapping(target = "matriz", ignore = true)
    Tarefa toEntity(CriarTarefaDto dto);
    
    @org.mapstruct.Mapping(source = "categoria.id", target = "categoriaId")
    CriarTarefaDto toDto(Tarefa tarefa);
    
    @org.mapstruct.Mapping(source = "categoria.id", target = "categoriaId")
    @org.mapstruct.Mapping(source = "categoria.nome", target = "categoriaNome")
    TarefaDTO toTarefaDTO(Tarefa tarefa);
    List<TarefaDTO> toTarefaDTOList(List<Tarefa> tarefas);

    // categoria map
    @org.mapstruct.Mapping(target = "usuario", ignore = true)
    @org.mapstruct.Mapping(target = "id", ignore = true)
    @org.mapstruct.Mapping(target = "tarefas", ignore = true)
    Categoria toEntity(CriarCategoriaDTO dto);
    CriarCategoriaDTO toDto(Categoria categoria);
    CategoriaDTO toCategoriaDTO(Categoria categoria);
    List<CategoriaDTO> toCategoriaDTOList(List<Categoria> categorias);
    List<CategoriaSimplesDTO> toCategoriaSimplesDTOList(List<Categoria> categorias);

}