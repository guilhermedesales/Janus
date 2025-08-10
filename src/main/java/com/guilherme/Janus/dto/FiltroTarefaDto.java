package com.guilherme.Janus.dto;

import com.guilherme.Janus.model.CategoriaTarefa;
import com.guilherme.Janus.model.enums.Prioridade;
import com.guilherme.Janus.model.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FiltroTarefaDto {

    private Prioridade prioridade;
    private CategoriaTarefa categoriaTarefa;
    private String tipoData;
    private List<Status> statusSelec;



}
