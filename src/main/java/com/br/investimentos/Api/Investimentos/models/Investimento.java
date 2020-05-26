package com.br.investimentos.Api.Investimentos.models;

import com.br.investimentos.Api.Investimentos.enums.RiscoInvestimento;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Investimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O nome do investimento não pode ser nulo ou vazio!")
    private String nomeInvestimento;

    @NotNull(message = "A descrição do Investimento não pode ser nula!")
    private String descricaoInvestimento;

    @NotNull(message = "O risco do Investimento não pode ser nulo!")
    private RiscoInvestimento riscoInvestimento;

    @NotNull(message = "A porcentagem de Lucro do Investimento não pode ser nula!")
    @DecimalMin(value = "0.1", message = "O valor mínimo da porcentagem de lucro deve ser 0.1% !")
    private Double porcentagemLucro;

    public Investimento(Integer id, String nomeInvestimento, String descricaoInvestimento, RiscoInvestimento riscoInvestimento,Double porcentagemLucro) {
        this.id = id;
        this.nomeInvestimento = nomeInvestimento;
        this.descricaoInvestimento = descricaoInvestimento;
        this.riscoInvestimento = riscoInvestimento;
        this.porcentagemLucro = porcentagemLucro;
    }

    public Investimento() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeInvestimento() {
        return nomeInvestimento;
    }

    public void setNomeInvestimento(String nomeInvestimento) {
        this.nomeInvestimento = nomeInvestimento;
    }

    public String getDescricaoInvestimento() {
        return descricaoInvestimento;
    }

    public void setDescricaoInvestimento(String descricaoInvestimento) {
        this.descricaoInvestimento = descricaoInvestimento;
    }

    public RiscoInvestimento getRiscoInvestimento() {
        return riscoInvestimento;
    }

    public void setRiscoInvestimento(RiscoInvestimento riscoInvestimento) {
        this.riscoInvestimento = riscoInvestimento;
    }

    public Double getPorcentagemLucro() {
        return porcentagemLucro;
    }

    public void setPorcentagemLucro(Double porcentagemLucro) {
        this.porcentagemLucro = porcentagemLucro;
    }
}
