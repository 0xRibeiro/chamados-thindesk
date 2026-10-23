package com.microservico.chamadosthindesk.config;

import org.mongodb.spring.session.config.annotation.web.http.EnableMongoHttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

// usa as sessoes do MongoDB
@EnableMongoHttpSession
public class ConfiguracaoSeguranca {

    @Bean
    public SecurityFilterChain configurarAcessos(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(regras -> regras
        .requestMatchers("/error").permitAll()
        .anyRequest().authenticated()).exceptionHandling(erros
        -> erros.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        // retorna 401 quando uma requisição exige autenticação

        return http.build();
    }
}