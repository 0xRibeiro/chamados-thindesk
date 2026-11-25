package com.microservico.chamadosthindesk.config;

import com.microservico.chamadosthindesk.model.Usuario;
import com.microservico.chamadosthindesk.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// cria o administrador inicial quando a aplicacao inicia.
@Component
public class AdminInicial implements CommandLineRunner {

    private final UsuarioRepository repositorioUsuarios;
    private final PasswordEncoder codificadorSenha;

    // usa valores do .env para não ficarem publicos
    @Value("${ADMIN_EMAIL:}")
    private String emailAdmin;

    @Value("${ADMIN_PASSWORD:}")
    private String senhaAdmin;

    public AdminInicial(UsuarioRepository repositorioUsuarios,
                        PasswordEncoder codificadorSenha) {
        this.repositorioUsuarios = repositorioUsuarios;
        this.codificadorSenha = codificadorSenha;
    }

    @Override
    public void run(String... args) {

        // so cria o administrador se as credenciais estiverem configuradas.
        if (emailAdmin.isBlank() || senhaAdmin.isBlank()) {
            System.out.println("Administrador inicial nao configurado.");
            return;
        }

        // evita criar a mesma conta toda vez que o sistema iniciar.
        if (repositorioUsuarios.findByEmail(emailAdmin.trim().toLowerCase()).isPresent()) {
            return;
        }

        Usuario admin = new Usuario();
        admin.setNome("Administrador");
        admin.setEmail(emailAdmin.trim().toLowerCase());
        admin.setSenha(codificadorSenha.encode(senhaAdmin));
        admin.setPapel("Admin");
        admin.setAtivo(true);

        repositorioUsuarios.save(admin);

        System.out.println("Administrador inicial criado.");
    }
}
