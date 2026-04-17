package com.guilherme.Janus.Domain.Entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
//import lombok.*;

@Entity
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;
    @Column(name = "descricao")
    private String desc;
    private String icon; // lucid icon
    private String cor; // cor hexa

    @ManyToOne
    private Usuario usuario;

    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private List<Tarefa> tarefas; // uma tarefa tem uma categoria e uma categoria pode ter muitas tarefas

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Categoria(){}

    // construtor
    public Categoria(String nome, String desc, String icon, String cor, Usuario usuario) {
        this.nome = nome;
        this.desc = desc;
        this.usuario = usuario;
        this.icon = icon;
        this.cor = cor;
    }

    // @PrePersist
    // public void generateId() {
    //     if (this.id == null) {
    //         this.id = UUID.randomUUID();
    //     }
    // }

    ///// getters e setters //////

    // get e set nome
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    // get e set descrição
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {return icon;}
    public void setIcon(String icon) {this.icon = icon;}

    public String getCor() {return cor;}
    public void setCor(String cor) {this.cor = cor;}

    //get e set usuario
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // get e set id
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    // get e set tarefas
    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }
}
