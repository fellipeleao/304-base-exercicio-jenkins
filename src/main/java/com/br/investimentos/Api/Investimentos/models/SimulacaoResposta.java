package com.br.investimentos.Api.Investimentos.models;

public class SimulacaoResposta {

    private Double resultadoSimulacao;

    public SimulacaoResposta(Double resultadoSimulacao) {
        this.resultadoSimulacao = resultadoSimulacao;
    }

    public SimulacaoResposta() {
    }

    public Double getResultadoSimulacao() {
        return resultadoSimulacao;
    }

    public void setResultadoSimulacao(Double resultadoSimulacao) {
        this.resultadoSimulacao = resultadoSimulacao;
    }
}
