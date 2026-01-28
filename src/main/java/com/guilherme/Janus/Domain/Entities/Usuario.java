package com.guilherme.Janus.Domain.Entities;

import java.util.UUID;

import jakarta.persistence.*;
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

@Entity
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String nome;

    @Column(unique = true)
    private String email;
    private String senha;

    public Usuario() {}

    public Usuario(UUID id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // @PrePersist
    // public void generateId() {
    //     if (this.id == null) {
    //         this.id = UUID.randomUUID();
    //     }
    // }

    ///// getters e setters //////
    
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }

}
