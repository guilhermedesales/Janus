package com.guilherme.Janus.Application.DTOs.Tarefa;

import java.time.LocalDate;
import java.util.UUID;

import com.guilherme.Janus.Domain.enums.Prioridade;
import com.guilherme.Janus.Domain.enums.Status;

public class CriarTarefaDto {

    private String titulo;
    private String desc;
    private Prioridade prioridade;
    //private Status status =  Status.EM_ANDAMENTO;
    private LocalDate dt_ini;
    private LocalDate dt_fim;
    private UUID categoriaId;

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

    public LocalDate getDt_ini() {
        return dt_ini;
    }
    public void setDt_ini(LocalDate dt_ini) {
        this.dt_ini = dt_ini;
    }

    public LocalDate getDt_fim() {
        return dt_fim;
    }
    public void setDt_fim(LocalDate dt_fim) {this.dt_fim = dt_fim;}

//    public Status getStatus() {return status;}
//    public void setStatus(Status status) {this.status = status;}

    public Prioridade getPrioridade() {return prioridade;}
    public void setPrioridade(Prioridade prioridade) {this.prioridade = prioridade;}

    public UUID getCategoriaId() {
        return categoriaId;
    }
    public void setCategoriaId(UUID categoriaId) {
        this.categoriaId = categoriaId;
    } 

}
