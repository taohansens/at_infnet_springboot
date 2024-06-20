package org.taohansen.at_infnet_springboot.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.taohansen.at_infnet_springboot.entities.Usuario;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Usuario findUsuarioByNome(String nome);
}