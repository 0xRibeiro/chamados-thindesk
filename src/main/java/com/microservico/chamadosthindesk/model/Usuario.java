package com.microservico.chamadosthindesk.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// classe dos usuarios do sistema sendo salvos na collection do mongodb
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;

    @NotBlank(message = "O nome é obrigatorio")
    private String nome;
    @NotBlank(message = "O email é obrigatorio")
    @Email(message = "digite um email válido")
    private String email;
    @NotBlank(message = "A senha é obrigatoria")
    private String senha;

    // o papel padrão do usuário será o comum.
    private String papel = "UsuarioComum";
    // ativo determina se a conta foi apagada ou não
    private boolean ativo = true;

    public Usuario() {
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPapel() {
        return papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public boolean taAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}