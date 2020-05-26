package com.br.investimentos.Api.Investimentos.services;

import com.br.investimentos.Api.Investimentos.models.Investimento;
import com.br.investimentos.Api.Investimentos.models.SimulacaoCadastro;
import com.br.investimentos.Api.Investimentos.models.SimulacaoResposta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimulacaoService {

    @Autowired
    private InvestimentoService investimentoService;

    public SimulacaoResposta realizarSimulacao(SimulacaoCadastro simulacaoCadastro) {
        Investimento investimento = investimentoService.buscarPorId(simulacaoCadastro.getInvestimentoId()).get();
        calcularRendimentoSimulado(simulacaoCadastro, investimento);

        SimulacaoResposta simulacaoResposta = new SimulacaoResposta();
        simulacaoResposta.setResultadoSimulacao(calcularRendimentoSimulado(simulacaoCadastro, investimento));
        return simulacaoResposta;
    }

    private Double calcularRendimentoSimulado(SimulacaoCadastro simulacaoCadastro, Investimento investimento) {
        return simulacaoCadastro.getDinheiroAplicado()
                * simulacaoCadastro.getMesesDeAplicacao()
                * investimento.getPorcentagemLucro();
    }
}
