package com.guilherme.Janus.Domain.Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guilherme.Janus.Domain.enums.MatrizEisenhower;
import com.guilherme.Janus.Domain.enums.Prioridade;
import com.guilherme.Janus.Domain.enums.Status;
//import java.util.List;

import jakarta.persistence.*;
//import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

//@Getter
//@Setter
@Entity
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    private String titulo;
    private String desc;

    @Enumerated(EnumType.STRING)
    private Status status = Status.EM_ANDAMENTO;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dt_ini;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dt_fim;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private MatrizEisenhower matriz;

    private LocalDateTime CreatedAt;
    private LocalDateTime UpdatedAt;

    public Tarefa(){}

    public Tarefa(UUID id, Usuario usuario,String titulo, String desc, Status status, Prioridade prioridade, LocalDate dt_ini, LocalDate dt_fim, Categoria categoria, MatrizEisenhower matriz) {
        this.id = id;
        this.usuario = usuario;
        this.titulo = titulo;
        this.desc = desc;
        this.status = Status.EM_ANDAMENTO;
        this.prioridade = prioridade;
        this.dt_ini = dt_ini;
        this.dt_fim = dt_fim;
        this.categoria = categoria;
        this.matriz = matriz;
    }

    // @PrePersist
    // public void generateId() {
    //     if (this.id == null) {
    //         this.id = UUID.randomUUID();
    //     }
    // }

    ///// getters e setters //////

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

    public LocalDate getDt_ini() {
        return dt_ini;
    }
    public void setDt_ini(LocalDate dt_ini) {
        this.dt_ini = dt_ini;
    }

    public LocalDate getDt_fim() {
        return dt_fim;
    }
    public void setDt_fim(LocalDate dt_fim) {
        this.dt_fim = dt_fim;
    }

    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public MatrizEisenhower getMatriz() {
        return matriz;
    }
    public void setMatriz(MatrizEisenhower matriz) {
        this.matriz = matriz;
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

}