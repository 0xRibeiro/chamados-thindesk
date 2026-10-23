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
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

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

    // metodo da rota de listagem de chamados
    @GetMapping("/usuario/{id}")
    public List<Chamado> listarChamadosPorUsuario(@PathVariable("id") String id,
                                                  Authentication autenticacao) {

        // identifica o usuario pela sessão autenticada
        String autorChamadoId = chamadoService.buscarUsuarioId(autenticacao.getName());

        // impede que um usuario consulte chamados de outra pessoa pela URL
        if (!autorChamadoId.equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você só pode consultar seus próprios chamados.");
        }

        return chamadoService.listarChamadosPorUsuario(id);
    }
}