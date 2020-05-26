package com.br.investimentos.Api.Investimentos.services;

import com.br.investimentos.Api.Investimentos.models.Investimento;
import com.br.investimentos.Api.Investimentos.models.SimulacaoCadastro;
import com.br.investimentos.Api.Investimentos.models.SimulacaoResposta;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

@SpringBootTest
public class SimulacaoServiceTest {

    @MockBean
    InvestimentoService investimentoService;

    @Autowired
    SimulacaoService simulacaoService;

    Investimento investimento;
    SimulacaoCadastro simulacaoCadastro;


    @Test
    public void deveRealizarSimulacaoComSucesso() {
        investimento = new Investimento();
        investimento.setPorcentagemLucro(10.0);

        simulacaoCadastro = new SimulacaoCadastro();
        simulacaoCadastro.setDinheiroAplicado(10.0);
        simulacaoCadastro.setMesesDeAplicacao(10);


        Mockito.when(investimentoService.buscarPorId(Mockito.anyInt())).thenReturn(Optional.of(investimento));
        SimulacaoResposta simulacaoResposta = simulacaoService.realizarSimulacao(simulacaoCadastro);
        Assertions.assertEquals(1000.0, simulacaoResposta.getResultadoSimulacao());
    }

    @Test
    public void deveRealizarSimulacaoComComErroPoisNaoExisteIdInvestimento() {
        Mockito.when(investimentoService.buscarPorId(Mockito.anyInt())).thenThrow(ObjectNotFoundException.class);

        Assertions.assertThrows(ObjectNotFoundException.class, () -> simulacaoService.realizarSimulacao(new SimulacaoCadastro()));
    }


}
