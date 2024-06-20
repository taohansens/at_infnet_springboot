package org.taohansen.at_infnet_springboot.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.taohansen.at_infnet_springboot.dto.DepartamentoDTO;
import org.taohansen.at_infnet_springboot.entities.Departamento;
import org.taohansen.at_infnet_springboot.repositories.DepartamentoRepository;
import org.taohansen.at_infnet_springboot.services.exceptions.DatabaseException;
import org.taohansen.at_infnet_springboot.services.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService {
    @Autowired
    private DepartamentoRepository repository;

    @Transactional(readOnly = true)
    public List<DepartamentoDTO> findAll() {
        List<Departamento> list = repository.findAll();
        return list.stream().map(DepartamentoDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public DepartamentoDTO findById(Long id) {
        Optional<Departamento> obj = repository.findById(id);
        Departamento entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Departamento not found."));
        return new DepartamentoDTO(entity);
    }

    @Transactional
    public DepartamentoDTO insert(DepartamentoDTO dto) {
        Departamento entity = new Departamento();
        entity.setNome(dto.getNome());
        entity.setLocal(dto.getLocal());
        entity = repository.save(entity);
        return new DepartamentoDTO(entity);
    }

    @Transactional
    public DepartamentoDTO update(Long id, DepartamentoDTO dto) {
        try {
            Departamento entity = repository.getReferenceById(id);
            entity.setNome(dto.getNome());
            entity.setNome(dto.getNome());
            entity.setLocal(dto.getLocal());
            entity = repository.save(entity);
            return new DepartamentoDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id " + id + " not found");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Departamento id (" + id + ") not found.");
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Database Integrity Violation");
        }
    }

}
