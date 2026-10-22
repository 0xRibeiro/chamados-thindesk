package com.microservico.chamadosthindesk.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

// essa entidade guarda os dados do chamado, desde seu titulo e descrição até
// os detalhes da solução.
@Document(collection = "chamados")
public class Chamado {

    @Id
    private String id;

    @NotBlank(message = "O título é obrigatório.")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória.")
    private String descricao;

    // preenchidos pelo sistema
    private String autorChamadoId;
    private Instant dataAbertura;
    private StatusChamado status;

    // preenchido pelo tecnico
    private String solucao;

    private Instant dataSolucao;

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAutorChamadoId() {
        return autorChamadoId;
    }

    public void setAutorChamadoId(String autorChamadoId) {
        this.autorChamadoId = autorChamadoId;
    }

    public Instant getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Instant dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }

    public String getSolucao() {
        return solucao;
    }

    public void setSolucao(String solucao) {
        this.solucao = solucao;
    }

    public Instant getDataSolucao() {
        return dataSolucao;
    }

    public void setDataSolucao(Instant dataSolucao) {
        this.dataSolucao = dataSolucao;
    }
}