package com.guilherme.Janus.dto;

import com.guilherme.Janus.model.enums.Prioridade;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TarefaDto {

    private String titulo;
    private String desc;

    private Prioridade prioridade;
    private LocalDate dt_ini;
    private LocalDate dt_fim;
    private Long categoriaId;

    public TarefaDto(){}

    public TarefaDto(String titulo, String desc, Prioridade prioridade, LocalDate dt_ini, LocalDate dt_fim, Long categoriaId) {
        this.titulo = titulo;
        this.desc = desc;

        this.prioridade = prioridade;
        this.dt_ini = dt_ini;
        this.dt_fim = dt_fim;
        this.categoriaId = categoriaId;
    }

}
