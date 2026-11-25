
package com.microservico.chamadosthindesk.repository;

import com.microservico.chamadosthindesk.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

// interface para operações basicas
public interface UsuarioRepository extends MongoRepository<Usuario, String> {

    // usca um usuario pelo email usado no login.
    Optional<Usuario> findByEmail(String email);

    // busca usuarios ativos no sistema
    List<Usuario> findByAtivoTrue();
}