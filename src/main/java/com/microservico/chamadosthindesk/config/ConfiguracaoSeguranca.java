package com.microservico.chamadosthindesk.config;

import com.microservico.chamadosthindesk.model.Usuario;
import com.microservico.chamadosthindesk.repository.UsuarioRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

// Regras de autenticacao e autorizacao do ThinDesk.
@Configuration
public class ConfiguracaoSeguranca implements UserDetailsService {

    private final UsuarioRepository repositorioUsuarios;

    public ConfiguracaoSeguranca(UsuarioRepository repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    @Bean
    public SecurityFilterChain configurarAcessos(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(regras -> regras
                .requestMatchers("/login", "/cadastro", "/css/**", "/error").permitAll()

                .requestMatchers("/admin/**").hasRole("Admin")

                .requestMatchers("/tecnico/**")
                .hasAnyRole("UsuarioTecnico", "Admin")

                .requestMatchers("/comum/**")
                .hasAnyRole("UsuarioComum", "UsuarioTecnico", "Admin")

                .requestMatchers(HttpMethod.PUT, "/api/chamados/*/resolver")
                .hasRole("UsuarioTecnico")

                .requestMatchers("/api/chamados/**").authenticated()

                .requestMatchers("/", "/inicio").authenticated()

                .anyRequest().authenticated()
        );

        http.formLogin(login -> login
                .loginPage("/login")
                .usernameParameter("email")
                .defaultSuccessUrl("/inicio", true)
                .permitAll()
        );

        http.logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
        );

        return http.build();
    }

    @Override
    public UserDetails loadUserByUsername(String email) {

        Usuario usuario = repositorioUsuarios
                .findByEmail(email.trim().toLowerCase())
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario nao encontrado"));

        return User.withUsername(usuario.getEmail())
                .password(usuario.getSenha())
                .roles(usuario.getPapel())
                .disabled(!usuario.taAtivo())
                .build();
    }
}
