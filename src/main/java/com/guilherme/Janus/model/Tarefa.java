package com.guilherme.Janus.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guilherme.Janus.model.enums.MatrizEisenhower;
import com.guilherme.Janus.model.enums.Prioridade;
import com.guilherme.Janus.model.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
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

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaTarefa categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private MatrizEisenhower matriz;

    public Tarefa(){}

    public Tarefa(Long id, Usuario usuario,String titulo, String desc, Status status, Prioridade prioridade, LocalDate dt_ini, LocalDate dt_fim, CategoriaTarefa categoria, MatrizEisenhower matriz) {
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

}