package com.guilherme.Janus.model;

import com.guilherme.Janus.model.enums.Prioridade;
import com.guilherme.Janus.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity

public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String titulo;
    private String desc;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    private Date dt_ini;
    private Date dt_fim;

    @ManyToOne
    private CategoriaTarefa categoria;

    public Tarefa(){}

    public Tarefa(Long id, String titulo, String desc, Status status, Prioridade prioridade, Date dt_ini, Date dt_fim, CategoriaTarefa categoria) {
        this.id = id;
        this.titulo = titulo;
        this.desc = desc;
        this.status = status;
        this.prioridade = prioridade;
        this.dt_ini = dt_ini;
        this.dt_fim = dt_fim;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public CategoriaTarefa getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaTarefa categoria) {
        this.categoria = categoria;
    }
}