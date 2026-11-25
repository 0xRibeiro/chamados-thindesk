package com.microservico.chamadosthindesk.controller;

import com.microservico.chamadosthindesk.model.Usuario;
import com.microservico.chamadosthindesk.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

// Controla as operacoes administrativas dos usuarios.
@Controller
public class AdminController {

    private final UsuarioService usuarioService;

    public AdminController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Mostra a lista de usuarios cadastrados.
    @GetMapping("/admin/usuarios")
    public String listarUsuarios(Model modelo) {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);

        return "admin-usuarios";
    }

    // Altera o papel de um usuario.
    @PostMapping("/admin/usuarios/papel")
    public String alterarPapel(@RequestParam String id,
                               @RequestParam String papel,
                               RedirectAttributes redirecionamento) {
        try {
            usuarioService.alterarPapel(id, papel);
            redirecionamento.addFlashAttribute(
                    "mensagem", "Papel do usuario alterado com sucesso.");
        } catch (IllegalArgumentException erro) {
            redirecionamento.addFlashAttribute(
                    "erro", erro.getMessage());
        }

        return "redirect:/admin/usuarios";
    }
}