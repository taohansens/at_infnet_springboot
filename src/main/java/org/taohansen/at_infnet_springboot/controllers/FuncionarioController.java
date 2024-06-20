package org.taohansen.at_infnet_springboot.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.taohansen.at_infnet_springboot.dto.FuncionarioDTO;
import org.taohansen.at_infnet_springboot.services.FuncionarioService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/funcionarios")
public class FuncionarioController {
    @Autowired
    private FuncionarioService service;

    @GetMapping
    public ResponseEntity<List<FuncionarioDTO>> findAll() {
        List<FuncionarioDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FuncionarioDTO> findById(@PathVariable Long id) {
        FuncionarioDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FuncionarioDTO> insert(@RequestBody FuncionarioDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<FuncionarioDTO> update(@PathVariable Long id, @RequestBody FuncionarioDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
