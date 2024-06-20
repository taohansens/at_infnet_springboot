package org.taohansen.at_infnet_springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.taohansen.at_infnet_springboot.entities.Departamento;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
}
