package org.taohansen.at_infnet_springboot.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.taohansen.at_infnet_springboot.dto.FuncionarioDTO;
import org.taohansen.at_infnet_springboot.entities.Funcionario;
import org.taohansen.at_infnet_springboot.entities.Departamento;
import org.taohansen.at_infnet_springboot.repositories.DepartamentoRepository;
import org.taohansen.at_infnet_springboot.repositories.FuncionarioRepository;
import org.taohansen.at_infnet_springboot.services.exceptions.DatabaseException;
import org.taohansen.at_infnet_springboot.services.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;
    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Transactional(readOnly = true)
    public List<FuncionarioDTO> findAll() {
        List<Funcionario> list = repository.findAll();
        return list.stream()
                .map(FuncionarioDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FuncionarioDTO findById(Long id) {
        Optional<Funcionario> obj = repository.findById(id);
        Funcionario entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Funcionario not found."));
        return new FuncionarioDTO(entity);
    }

    @Transactional
    public FuncionarioDTO insert(FuncionarioDTO dto) {
        Funcionario entity = new Funcionario();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new FuncionarioDTO(entity);
    }

    @Transactional
    public FuncionarioDTO update(Long id, FuncionarioDTO dto) {
        try {
            Funcionario entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new FuncionarioDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id " + id + " not found");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Funcionario id (" + id + ") not found.");
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Database Integrity Violation");
        }
    }

    private void copyDtoToEntity(FuncionarioDTO dto, Funcionario entity) {
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setEndereco(dto.getEndereco());
        entity.setDataNascimento(dto.getDataNascimento());
        entity.setTelefone(dto.getTelefone());

        Departamento departamento = departamentoRepository.getReferenceById(dto.getDepartamento().getId());
        entity.setDepartamento(departamento);
    }

}
