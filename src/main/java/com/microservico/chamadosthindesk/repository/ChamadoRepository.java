package com.microservico.chamadosthindesk.repository;

import com.microservico.chamadosthindesk.model.Chamado;
import com.microservico.chamadosthindesk.model.StatusChamado;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ChamadoRepository extends MongoRepository<Chamado, String> {

    // busca os chamados vinculados ao usuario
    List<Chamado> findByAutorChamadoId(String autorChamadoId);

    List<Chamado> findByStatus(StatusChamado status);

    long countByStatus(StatusChamado status);
}

