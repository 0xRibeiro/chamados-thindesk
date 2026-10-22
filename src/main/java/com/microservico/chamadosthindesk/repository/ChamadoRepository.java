package com.microservico.chamadosthindesk.repository;

import com.microservico.chamadosthindesk.model.Chamado;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ChamadoRepository extends MongoRepository<Chamado, String> {

    // busca os chamados vinculados ao usuario
    List<Chamado> findByAutorChamadoId(String autorChamadoId);
}