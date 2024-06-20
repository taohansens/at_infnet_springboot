package org.taohansen.at_infnet_springboot.dto;

import org.taohansen.at_infnet_springboot.entities.Usuario;

public class UsuarioDTO {
    private String id;
    private String nome;
    private String senha;
    private String papel;

    public UsuarioDTO() {
    }

    public UsuarioDTO(String id, String nome, String senha, String papel) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.papel = papel;
    }

    public UsuarioDTO(Usuario entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.senha = entity.getSenha();
        this.papel = entity.getPapel();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }
}
