package com.guilherme.Janus.Application.DTOs.Categoria;

public class CriarCategoriaDTO {
    public String nome;
    public String desc;
    public String icon;
    public String cor;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDesc() {return desc;}
    public void setDesc(String desc) {this.desc = desc;}

    public String getIcon() {return icon;}
    public void setIcon(String icon) {this.icon = icon;}

    public String getCor() {return cor;}
    public void setCor(String cor) {this.cor = cor;}

}
