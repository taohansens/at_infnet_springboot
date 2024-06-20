package org.taohansen.at_infnet_springboot.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.taohansen.at_infnet_springboot.entities.Funcionario;

import java.time.LocalDateTime;

public class FuncionarioDTO {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;
    private LocalDateTime dataNascimento;
    private DepartamentoDTO departamento;

    public FuncionarioDTO() {
    }

    public FuncionarioDTO(Funcionario entity) {
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.endereco = entity.getEndereco();
        this.telefone = entity.getTelefone();
        this.email = entity.getEmail();
        this.dataNascimento = entity.getDataNascimento();
        this.departamento = new DepartamentoDTO(entity.getDepartamento());
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

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDateTime dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public DepartamentoDTO getDepartamento() {
        return departamento;
    }

    public void setDepartamento(DepartamentoDTO departamento) {
        this.departamento = departamento;
    }
}
