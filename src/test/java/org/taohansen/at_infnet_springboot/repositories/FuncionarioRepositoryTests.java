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
import org.taohansen.at_infnet_springboot.entities.Funcionario;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Transactional
public class FuncionarioRepositoryTests {

    @Autowired
    private FuncionarioRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalFuncionarios;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;

        repository.deleteAll();

        Funcionario funcionario = new Funcionario();
        funcionario.setId(existingId);
        repository.save(funcionario);

        countTotalFuncionarios = repository.count();
    }

    @Test
    public void findByIdShouldReturnOptionalEmptyWhenIdDoesNotExist() {
        Optional<Funcionario> result = repository.findById(nonExistingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull(){
        Funcionario funcionario = new Funcionario();
        funcionario.setId(null);

        funcionario = repository.save(funcionario);

        Assertions.assertNotNull(funcionario.getId());
        Assertions.assertEquals(countTotalFuncionarios + 1, funcionario.getId());
    }
    @Test
    public void countShouldReturnTotalNumberOfFuncionarios() {
        long count = repository.count();
        Assertions.assertEquals(countTotalFuncionarios, count);
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists(){
        repository.deleteById(existingId);
        Optional<Funcionario> result = repository.findById(existingId);

        Assertions.assertFalse(result.isPresent());
    }
}