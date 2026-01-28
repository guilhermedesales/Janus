package com.guilherme.Janus.Application.DTOs.Usuario;

// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
public class UsuarioDto {
    private String nome;
    private String email;
    private String senha; // remover da visualização do dto

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
