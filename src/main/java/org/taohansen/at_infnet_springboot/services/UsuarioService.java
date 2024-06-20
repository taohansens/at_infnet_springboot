package org.taohansen.at_infnet_springboot.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.taohansen.at_infnet_springboot.dto.UsuarioDTO;
import org.taohansen.at_infnet_springboot.entities.Usuario;
import org.taohansen.at_infnet_springboot.repositories.UsuarioRepository;
import org.taohansen.at_infnet_springboot.services.exceptions.DatabaseException;
import org.taohansen.at_infnet_springboot.services.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repository;

    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll() {
        List<Usuario> list = repository.findAll();
        return list.stream().map(UsuarioDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public UsuarioDTO findById(String id) {
        Optional<Usuario> obj = repository.findById(id);
        Usuario entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Usuario not found."));
        return new UsuarioDTO(entity);
    }

    @Transactional
    public UsuarioDTO insert(UsuarioDTO dto) {
        Usuario entity = new Usuario();
        entity.setNome(dto.getNome());
        entity.setPapel(dto.getPapel());
        entity.setSenha(new BCryptPasswordEncoder().encode(dto.getSenha()));
        entity = repository.save(entity);
        return new UsuarioDTO(entity);
    }

    @Transactional
    public UsuarioDTO update(String id, UsuarioDTO dto) {
        try {
            Optional<Usuario> obj = repository.findById(id);
            Usuario entity = obj.orElseThrow(EntityNotFoundException::new);
            entity.setNome(dto.getNome());
            entity.setPapel(dto.getPapel());
            entity.setSenha(dto.getSenha());
            entity = repository.save(entity);
            return new UsuarioDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id " + id + " not found");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario id (" + id + ") not found.");
        }
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Database Integrity Violation");
        }
    }

}
