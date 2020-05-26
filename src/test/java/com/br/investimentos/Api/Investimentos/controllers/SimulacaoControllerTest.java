package com.br.investimentos.Api.Investimentos.controllers;

import com.br.investimentos.Api.Investimentos.models.Investimento;
import com.br.investimentos.Api.Investimentos.models.SimulacaoCadastro;
import com.br.investimentos.Api.Investimentos.models.SimulacaoResposta;
import com.br.investimentos.Api.Investimentos.services.SimulacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(SimulacaoController.class)
public class SimulacaoControllerTest {

    @MockBean
    private SimulacaoService simulacaoService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    public void deveRealizarSimulacaoComSucesso() throws Exception {
        SimulacaoResposta simulacaoResposta = new SimulacaoResposta(1000.0);
        Mockito.when(simulacaoService.realizarSimulacao(Mockito.any(SimulacaoCadastro.class))).thenReturn(simulacaoResposta);
        String json = mapper.writeValueAsString(simulacaoResposta);

        mockMvc.perform(MockMvcRequestBuilders.put("/simulacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.resultadoSimulacao", CoreMatchers.equalTo(1000.0)));
    }

    @Test
    public void deveRealizarSimulacaoComErro() throws Exception {
        SimulacaoResposta simulacaoResposta = new SimulacaoResposta(1000.0);
        Mockito.when(simulacaoService.realizarSimulacao(Mockito.any(SimulacaoCadastro.class))).thenThrow(RuntimeException.class);
        String json = mapper.writeValueAsString(simulacaoResposta);

        mockMvc.perform(MockMvcRequestBuilders.put("/simulacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
