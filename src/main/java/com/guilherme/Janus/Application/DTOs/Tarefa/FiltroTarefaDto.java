package com.guilherme.Janus.Application.DTOs.Tarefa;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.guilherme.Janus.Domain.Entities.Categoria;
import com.guilherme.Janus.Domain.enums.Prioridade;
import com.guilherme.Janus.Domain.enums.Status;

@Getter
@Setter
public class FiltroTarefaDto {

    private Prioridade prioridade;
    private Categoria categoriaTarefa;
    private String tipoData;
    private List<Status> statusSelec;



}
