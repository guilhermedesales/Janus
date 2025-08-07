package com.guilherme.Janus.dto;

import com.guilherme.Janus.model.enums.Prioridade;
import com.guilherme.Janus.model.enums.Status;

import java.util.Date;

public class TarefaDto {

    private String titulo;
    private String desc;
    private Status status;
    private Prioridade prioridade;
    private Date dt_ini;
    private Date dt_fim;
    private Long categoriaId;

    public TarefaDto(){}

    public TarefaDto(String titulo, String desc, Status status, Prioridade prioridade, Date dt_ini, Date dt_fim, Long categoriaId) {
        this.titulo = titulo;
        this.desc = desc;
        this.status = status;
        this.prioridade = prioridade;
        this.dt_ini = dt_ini;
        this.dt_fim = dt_fim;
        this.categoriaId = categoriaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public Date getDt_ini() {
        return dt_ini;
    }

    public void setDt_ini(Date dt_ini) {
        this.dt_ini = dt_ini;
    }

    public Date getDt_fim() {
        return dt_fim;
    }

    public void setDt_fim(Date dt_fim) {
        this.dt_fim = dt_fim;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}
