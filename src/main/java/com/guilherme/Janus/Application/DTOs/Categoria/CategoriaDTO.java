package com.guilherme.Janus.Application.DTOs.Categoria;

import java.util.List;
import java.util.UUID;

import com.guilherme.Janus.Application.DTOs.Tarefa.TarefaCategoriaDTO;

public class CategoriaDTO {
    public UUID id;
    public String nome;
    public String desc;
    public List<TarefaCategoriaDTO> tarefas;
}
