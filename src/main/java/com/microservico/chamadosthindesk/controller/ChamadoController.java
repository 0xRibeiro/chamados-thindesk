package com.microservico.chamadosthindesk.controller;

import com.microservico.chamadosthindesk.model.Chamado;
import com.microservico.chamadosthindesk.service.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chamados")
public class ChamadoController {

    private final ChamadoService chamadoService;

    public ChamadoController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    // abre o chamado usando a identidade do usuario autenticado
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Chamado abrirChamado(@Valid @RequestBody Chamado dados, Authentication autenticacao) {
        String autorChamadoId = chamadoService.buscarUsuarioId(autenticacao.getName());
        return chamadoService.abrirChamado(dados, autorChamadoId);
    }
}