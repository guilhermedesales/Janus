package com.guilherme.Janus.Application.DTOs.Tarefa;

import com.guilherme.Janus.Domain.enums.MatrizEisenhower;
import com.guilherme.Janus.Domain.enums.Prioridade;
import com.guilherme.Janus.Domain.enums.Status;

import java.util.UUID;

public class TarefaCategoriaDTO {
    public UUID id;
    public String titulo;
    public String desc;
    public Prioridade prioridade;
    public Status status;
    public MatrizEisenhower matriz;
    public String dt_ini;
    public String dt_fim;
}
