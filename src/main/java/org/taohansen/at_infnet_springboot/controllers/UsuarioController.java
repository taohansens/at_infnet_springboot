package org.taohansen.at_infnet_springboot.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.taohansen.at_infnet_springboot.dto.UsuarioDTO;
import org.taohansen.at_infnet_springboot.services.UsuarioService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService service;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        List<UsuarioDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UsuarioDTO> findById(@PathVariable String id) {
        UsuarioDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping(value = "/create")
    public ResponseEntity<UsuarioDTO> insert(@RequestBody UsuarioDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UsuarioDTO> update(@PathVariable String id, @RequestBody UsuarioDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
