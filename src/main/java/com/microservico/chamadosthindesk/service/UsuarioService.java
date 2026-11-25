
package com.microservico.chamadosthindesk.service;

import com.microservico.chamadosthindesk.model.Usuario;
import com.microservico.chamadosthindesk.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// logica dos cadastros dos usuarios.
@Service
public class UsuarioService {

    private final UsuarioRepository repositorioUsuarios;
    private final PasswordEncoder codificadorSenha;

    // O Spring fornece os objetos que precisamos usar.
    public UsuarioService(UsuarioRepository repositorioUsuarios,
                          PasswordEncoder codificadorSenha) {
        this.repositorioUsuarios = repositorioUsuarios;
        this.codificadorSenha = codificadorSenha;
    }

    public void cadastrarUsuario(String nome, String email, String senha) {

        // padroniza os emails em minusculo
        email = email.trim().toLowerCase();

        // usa o repository para evitar criar conta com o mesmo email
        if (repositorioUsuarios.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Este email ja esta cadastrado.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);

        // salva o hash da senha original
        usuario.setSenha(codificadorSenha.encode(senha));

        // o tipo de usuario salvo inicialmente sempre é o comum
        usuario.setPapel("UsuarioComum");
        usuario.setAtivo(true);

        repositorioUsuarios.save(usuario);
    }

    // lista os usuarios cadastrados no sistema.
    public java.util.List<Usuario> listarUsuarios() {
        return repositorioUsuarios.findAll();
    }

    // altera o papel de um usuario cadastrado. metodo de admin
    public Usuario alterarPapel(String id, String papel) {

        if (!papel.equals("UsuarioComum")
                && !papel.equals("UsuarioTecnico")
                && !papel.equals("Admin")) {
            throw new IllegalArgumentException("Papel invalido.");
        }

        Usuario usuario = repositorioUsuarios.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Usuario nao encontrado."));

        usuario.setPapel(papel);

        return repositorioUsuarios.save(usuario);
    }
}