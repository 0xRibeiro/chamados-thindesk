package com.microservico.chamadosthindesk.controller;

import com.microservico.chamadosthindesk.model.Chamado;
import com.microservico.chamadosthindesk.model.StatusChamado;
import com.microservico.chamadosthindesk.repository.ChamadoRepository;
import com.microservico.chamadosthindesk.service.ChamadoService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
public class RedirecionamentoControllador {

    private final ChamadoService chamadoService;
    private final ChamadoRepository chamadoRepository;

    public RedirecionamentoControllador(
            ChamadoService chamadoService,
            ChamadoRepository chamadoRepository) {
        this.chamadoService = chamadoService;
        this.chamadoRepository = chamadoRepository;
    }

    @GetMapping("/inicio")
    public String mostrarInicio(Authentication autenticacao) {

        if (autenticacao.getAuthorities().stream()
                .anyMatch(p -> Objects.equals(p.getAuthority(), "ROLE_Admin"))) {
            return "redirect:/admin/inicio";
        }

        if (autenticacao.getAuthorities().stream()
                .anyMatch(p -> Objects.equals(p.getAuthority(), "ROLE_UsuarioTecnico"))) {
            return "redirect:/tecnico/inicio";
        }

        return "redirect:/comum/inicio";
    }

    // Area do usuario comum
    @GetMapping("/comum/inicio")
    public String mostrarInicioComum(Authentication autenticacao, Model modelo) {

        String id = chamadoService.buscarUsuarioId(autenticacao.getName());

        modelo.addAttribute("novoChamado", new Chamado());
        modelo.addAttribute("chamados",
                chamadoService.listarChamadosPorUsuario(id));

        return "comum";
    }

    @PostMapping("/comum/chamados")
    public String abrirChamado(
            @ModelAttribute Chamado dados,
            Authentication autenticacao) {

        String id = chamadoService.buscarUsuarioId(autenticacao.getName());

        chamadoService.abrirChamado(dados, id);

        return "redirect:/comum/inicio";
    }

    // Area do tecnico
    @GetMapping("/tecnico/inicio")
    public String mostrarInicioTecnico(Model modelo) {

        modelo.addAttribute("chamados",
                chamadoRepository.findByStatus(StatusChamado.ABERTO));

        return "tecnico";
    }

    @PostMapping("/tecnico/chamados/{id}/resolver")
    public String resolverChamado(
            @PathVariable String id,
            @RequestParam String solucao) {

        chamadoService.resolverChamado(id, solucao);

        return "redirect:/tecnico/inicio";
    }

    // Dashboard administrativo
    @GetMapping("/admin/inicio")
    public String mostrarInicioAdmin(Model modelo) {

        modelo.addAttribute("total", chamadoRepository.count());

        modelo.addAttribute("abertos",
                chamadoRepository.countByStatus(StatusChamado.ABERTO));

        modelo.addAttribute("resolvidos",
                chamadoRepository.countByStatus(StatusChamado.RESOLVIDO));

        modelo.addAttribute("chamados", chamadoRepository.findAll());

        return "admin";
    }
}
