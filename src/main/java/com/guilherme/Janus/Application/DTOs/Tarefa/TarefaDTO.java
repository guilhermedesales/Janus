package com.guilherme.Janus.Application.DTOs.Tarefa;

import java.util.UUID;

public class TarefaDTO {
    public UUID id;
    public String titulo;
    public String desc;
    public String prioridade;
    public String dt_ini;
    public String dt_fim;
    public UUID categoriaId;
    public String categoriaNome;
}
