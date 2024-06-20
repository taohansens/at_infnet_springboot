package org.taohansen.at_infnet_springboot.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.taohansen.at_infnet_springboot.dto.DepartamentoDTO;
import org.taohansen.at_infnet_springboot.services.DepartamentoService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/departamentos")
public class DepartamentoController {
    @Autowired
    private DepartamentoService service;

    @GetMapping
    public ResponseEntity<List<DepartamentoDTO>> findAll() {
        List<DepartamentoDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DepartamentoDTO> findById(@PathVariable Long id) {
        DepartamentoDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartamentoDTO> insert(@RequestBody DepartamentoDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DepartamentoDTO> update(@PathVariable Long id, @RequestBody DepartamentoDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
