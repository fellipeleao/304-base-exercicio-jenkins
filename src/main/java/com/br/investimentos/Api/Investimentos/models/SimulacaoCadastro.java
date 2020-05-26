package com.br.investimentos.Api.Investimentos.models;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

public class SimulacaoCadastro {

    @NotBlank(message = "Id do Investimento não pode ser vazio ou nulo!")
    private int investimentoId;

    @NotBlank(message = "A quantidade de meses de aplicação não pode ser vazia ou nula!")
    private int mesesDeAplicacao;

    @NotBlank(message = "A quantidade de dinheiro aplicada não pode ser vazia ou nula!")
    @DecimalMin(value = "100.0", message = "O valor mínimo para investimento deve ser R$100.0!")
    private double dinheiroAplicado;

    public SimulacaoCadastro(int investimentoId, int mesesDeAplicacao, double dinheiroAplicado) {
        this.investimentoId = investimentoId;
        this.mesesDeAplicacao = mesesDeAplicacao;
        this.dinheiroAplicado = dinheiroAplicado;
    }

    public SimulacaoCadastro() {
    }

    public int getInvestimentoId() {
        return investimentoId;
    }

    public void setInvestimentoId(int investimentoId) {
        this.investimentoId = investimentoId;
    }

    public int getMesesDeAplicacao() {
        return mesesDeAplicacao;
    }

    public void setMesesDeAplicacao(int mesesDeAplicacao) {
        this.mesesDeAplicacao = mesesDeAplicacao;
    }

    public double getDinheiroAplicado() {
        return dinheiroAplicado;
    }

    public void setDinheiroAplicado(double dinheiroAplicado) {
        this.dinheiroAplicado = dinheiroAplicado;
    }
}
