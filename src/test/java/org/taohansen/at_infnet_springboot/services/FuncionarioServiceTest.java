package org.taohansen.at_infnet_springboot.services;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.taohansen.at_infnet_springboot.dto.DepartamentoDTO;
import org.taohansen.at_infnet_springboot.entities.Departamento;
import org.taohansen.at_infnet_springboot.entities.Funcionario;
import org.taohansen.at_infnet_springboot.repositories.DepartamentoRepository;
import org.taohansen.at_infnet_springboot.repositories.FuncionarioRepository;
import org.taohansen.at_infnet_springboot.services.exceptions.DatabaseException;
import org.taohansen.at_infnet_springboot.services.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class FuncionarioServiceTest {
    @InjectMocks
    private DepartamentoService service;
    @Mock
    private DepartamentoRepository repository;
    @Mock
    private FuncionarioRepository funcionarioRepository;

    private Long existingId;
    private Long nonExistingId;
    private Long dependentId;
    private Departamento departamento;

    @BeforeEach
    void setUp() throws Exception {
        existingId = 1L;
        nonExistingId = 1000L;
        dependentId = 20L;
        departamento = new Departamento();
        departamento.setId(existingId);
        departamento.setNome("Departamento 1");
        departamento.setLocal("Local 1");
        
        List<Departamento> departamentoList = List.of(departamento);

        Mockito.when(repository.existsById(existingId)).thenReturn(true);
        Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
        Mockito.when(repository.existsById(dependentId)).thenReturn(true);

        Mockito.when(repository.findAll()).thenReturn(departamentoList);

        Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(departamento);

        Mockito.when(repository.getReferenceById(existingId)).thenReturn(departamento);
        Mockito.doThrow(EntityNotFoundException.class).when(repository).getReferenceById(nonExistingId);

        Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(departamento));
        Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

        Mockito.doNothing().when(repository).deleteById(existingId);
        Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

        Mockito.when(funcionarioRepository.getReferenceById(ArgumentMatchers.any())).thenReturn(new Funcionario());
    }

    @Test
    public void findByIdShouldReturnObjectWhenIdExists() {
        DepartamentoDTO result = service.findById(existingId);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals(result.getNome(), "Departamento 1");
    }

    @Test
    public void findByIdShouldThrowExceptionWhenIdNonExists() {
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.findById(nonExistingId));
    }

    @Test
    public void findAllPagedShouldReturnList() {
        List<DepartamentoDTO> result = service.findAll();

        Assertions.assertNotNull(result);
        Mockito.verify(repository).findAll();
    }

    @Test
    public void updateShouldReturnObjectWhenIdExists() {
        String UPDATED_NAME = "OK";
        DepartamentoDTO dto = new DepartamentoDTO();
        dto.setNome(UPDATED_NAME);
        DepartamentoDTO result = service.update(existingId, dto);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(UPDATED_NAME, result.getNome());
    }

    @Test
    public void updateShouldThrowExceptionWhenIdNonExists() {
        DepartamentoDTO dto = new DepartamentoDTO();
        dto.setNome("UPDATED_NAME");
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> service.update(nonExistingId, dto));
    }

    @Test
    public void deleteShouldThrowDatabaseExceptionWhenIdDependent() {
        Assertions.assertThrows(DatabaseException.class,
                () -> service.delete(dependentId));
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> service.delete(nonExistingId));
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists() {
        Assertions.assertDoesNotThrow(() -> service.delete(existingId));
        Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
    }

}