package org.taohansen.at_infnet_springboot.repositories;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.taohansen.at_infnet_springboot.entities.Departamento;

import java.util.Optional;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Transactional
public class DepartarmentoRepositoryTests {

    @Autowired
    private DepartamentoRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalDepartamento;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;

        repository.deleteAll();
        
        Departamento departamento = new Departamento();
        departamento.setNome("Departamento 1");
        departamento.setLocal("Local 1");
        repository.save(departamento);
        
        countTotalDepartamento = repository.count();
    }

    @Test
    public void findByIdShouldReturnOptionalEmptyWhenIdDoesNotExist() {
        Optional<Departamento> result = repository.findById(nonExistingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull(){
        Departamento departamento = new Departamento();
        departamento.setId(null);

        departamento = repository.save(departamento);

        Assertions.assertNotNull(departamento.getId());
        Assertions.assertEquals(countTotalDepartamento + 1, departamento.getId());
    }
    @Test
    public void countShouldReturnTotalNumberOfFuncionarios() {
        long count = repository.count();
        Assertions.assertEquals(countTotalDepartamento, count);
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        repository.deleteById(existingId);
        Optional<Departamento> result = repository.findById(existingId);

        Assertions.assertFalse(result.isPresent());
    }
}