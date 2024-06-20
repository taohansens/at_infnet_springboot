package org.taohansen.at_infnet_springboot.dto;

import org.taohansen.at_infnet_springboot.entities.Departamento;

public class DepartamentoDTO {
    private Long id;
    private String nome;
    private String local;

    public DepartamentoDTO() {
    }

    public DepartamentoDTO(Departamento entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.local = entity.getLocal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
