package com.microservico.chamadosthindesk.service;

import com.microservico.chamadosthindesk.model.Chamado;
import com.microservico.chamadosthindesk.model.StatusChamado;
import com.microservico.chamadosthindesk.repository.ChamadoRepository;
// imports do mongodb para auxiliar na service
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

// regras de abertura, consulta e resolução dos chamados
@Service
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final MongoTemplate mongoTemplate;

    public ChamadoService(ChamadoRepository chamadoRepository, MongoTemplate mongoTemplate) {
        this.chamadoRepository = chamadoRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Chamado abrirChamado(Chamado dados, String autorChamadoId) {

        Chamado chamado = new Chamado();
        chamado.setTitulo(dados.getTitulo());
        chamado.setDescricao(dados.getDescricao());
        chamado.setAutorChamadoId(autorChamadoId);
        chamado.setDataAbertura(Instant.now());
        // todos são criados com o status ABERTO por padrão
        chamado.setStatus(StatusChamado.ABERTO);

        return chamadoRepository.save(chamado);
    }

    public List<Chamado> listarChamadosPorUsuario(String autorChamadoId) {
        return chamadoRepository.findByAutorChamadoId(autorChamadoId);
    }

    public Chamado buscarChamado(String id) {
        return chamadoRepository.findById(id).orElseThrow(()
        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chamado não encontrado."));
    }

    public Chamado resolverChamado(String id, String solucao) {

        // exige a solução preenchida
        if (solucao == null || solucao.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Preencher a solução é obrigatório.");
        }

        // retorna erro 404 caso o chamado não exista
        buscarChamado(id);

        // a atualização só poderá atingir um chamado ainda aberto
        Query consulta = new Query(Criteria.where("id").is(id).and("status").is(StatusChamado.ABERTO));

        Update atualizacao = new Update().set("solucao", solucao.trim()).set("status",
        StatusChamado.RESOLVIDO).set("dataSolucao", Instant.now());

        // confere o status e grava a solução na mesma operação utilizando o mongo Template
        Chamado chamadoResolvido = mongoTemplate.findAndModify(consulta, atualizacao,
        FindAndModifyOptions.options().returnNew(true), Chamado.class);

        if (chamadoResolvido == null) {throw new ResponseStatusException(HttpStatus.CONFLICT,
        "Este chamado já foi resolvido.");
        }

        return chamadoResolvido;
    }
}