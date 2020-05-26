package com.br.investimentos.Api.Investimentos.controllers;

import com.br.investimentos.Api.Investimentos.enums.RiscoInvestimento;
import com.br.investimentos.Api.Investimentos.models.Investimento;
import com.br.investimentos.Api.Investimentos.services.InvestimentoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Optional;

@WebMvcTest(InvestimentoController.class)
public class InvestimentoControllerTest {

    @MockBean
    InvestimentoService investimentoService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    Investimento investimento;

    @BeforeEach
    public void iniciar() {
        investimento = new Investimento();
        investimento.setRiscoInvestimento(RiscoInvestimento.ALTO);
        investimento.setPorcentagemLucro(10.0);
        investimento.setNomeInvestimento("Itau");
        investimento.setDescricaoInvestimento("Itau teste");
    }

    @Test
    public void deveSalvarInvestimentoComTodasAsInformacoes() throws Exception {
        Mockito.when(investimentoService.salvar(Mockito.any(Investimento.class))).thenReturn(investimento);
        String json = mapper.writeValueAsString(investimento);

        mockMvc.perform(MockMvcRequestBuilders.post("/investimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricaoInvestimento", CoreMatchers.equalTo("Itau teste")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeInvestimento", CoreMatchers.equalTo("Itau")));

    }

    @Test
    public void deveDarBadRequestCasoOsCamposSejamNulos() throws Exception {
        Mockito.when(investimentoService.salvar(Mockito.any(Investimento.class))).thenReturn(new Investimento());

        mockMvc.perform(MockMvcRequestBuilders.post("/investimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void deveDarBadRequestCasoGereAlgumErroParaSalvar() throws Exception {
        Mockito.when(investimentoService.salvar(Mockito.any(Investimento.class))).thenThrow(new RuntimeException());
        String json = mapper.writeValueAsString(investimento);

        mockMvc.perform(MockMvcRequestBuilders.post("/investimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deveCadastrarMesmoComDescricaoVazia() throws Exception {
        investimento.setDescricaoInvestimento("");
        Mockito.when(investimentoService.salvar(Mockito.any(Investimento.class))).thenReturn(investimento);
        String json = mapper.writeValueAsString(investimento);

        mockMvc.perform(MockMvcRequestBuilders.post("/investimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricaoInvestimento", CoreMatchers.equalTo("")));
    }

    @Test
    public void deveDarBadRequestCasoPasseUmJsonVazio() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/investimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deveBuscarPorId() throws Exception {
        Mockito.when(investimentoService.buscarPorId(Mockito.anyInt())).thenReturn(Optional.of(investimento));
        String json = mapper.writeValueAsString(investimento);

        mockMvc.perform(MockMvcRequestBuilders.get("/investimentos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricaoInvestimento", CoreMatchers.equalTo("Itau teste")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeInvestimento", CoreMatchers.equalTo("Itau")));
    }

    @Test
    public void deveDarBadQuestCasoTenhaExceptionAoBuscarId() throws Exception {
        Mockito.when(investimentoService.buscarPorId(Mockito.anyInt())).thenThrow(new RuntimeException());
        String json = mapper.writeValueAsString(investimento);

        mockMvc.perform(MockMvcRequestBuilders.get("/investimentos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deveBuscarTodosComUmIntegranteNaLista() throws Exception {
        Iterable<Investimento> listaDeInvestimentos = Arrays.asList(investimento);
        Mockito.when(investimentoService.buscarTodos()).thenReturn(listaDeInvestimentos);
        String json = mapper.writeValueAsString(listaDeInvestimentos);

        mockMvc.perform(MockMvcRequestBuilders.get("/investimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].descricaoInvestimento", CoreMatchers.equalTo("Itau teste")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nomeInvestimento", CoreMatchers.equalTo("Itau")));
    }

    @Test
    public void deveBuscarTodosComListaVazia() throws Exception {
        Iterable<Investimento> listaDeInvestimentos = Arrays.asList();
        Mockito.when(investimentoService.buscarTodos()).thenReturn(listaDeInvestimentos);
        String json = mapper.writeValueAsString(listaDeInvestimentos);

        mockMvc.perform(MockMvcRequestBuilders.get("/investimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void deveDarBadQuestCasoTenhaExceptionAoBuscarTodos() throws Exception {
        Iterable<Investimento> listaDeInvestimentos = Arrays.asList(investimento);
        Mockito.when(investimentoService.buscarTodos()).thenThrow(new RuntimeException());
        String json = mapper.writeValueAsString(listaDeInvestimentos);

        mockMvc.perform(MockMvcRequestBuilders.get("/investimentos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void deveDeletarPorId() throws Exception {
        Mockito.when(investimentoService.buscarPorId(Mockito.anyInt())).thenReturn(Optional.of(investimento));
        String json = mapper.writeValueAsString(investimento);

        mockMvc.perform(MockMvcRequestBuilders.delete("/investimentos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricaoInvestimento", CoreMatchers.equalTo("Itau teste")));

        Mockito.verify(investimentoService, Mockito.times(1)).deletar(investimento);
    }

    @Test
    public void deveDarNoContentCasoNaoConsigaBuscarPorId() throws Exception {
        Mockito.when(investimentoService.buscarPorId(Mockito.anyInt())).thenThrow(new RuntimeException());
        String json = mapper.writeValueAsString(investimento);

        mockMvc.perform(MockMvcRequestBuilders.delete("/investimentos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }


    @Test
    public void deveAtualizarInvestimentoComTodasAsInformacoes() throws Exception {
        Mockito.when(investimentoService.atualizar(Mockito.any(Investimento.class))).thenReturn(investimento);
        String json = mapper.writeValueAsString(investimento);

        mockMvc.perform(MockMvcRequestBuilders.put("/investimentos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricaoInvestimento", CoreMatchers.equalTo("Itau teste")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeInvestimento", CoreMatchers.equalTo("Itau")));

    }

    @Test
    public void deveDarBadRequestCasoTenhaErroAoAtualizar() throws Exception {
        Mockito.when(investimentoService.atualizar(Mockito.any(Investimento.class))).thenThrow(new RuntimeException());
        String json = mapper.writeValueAsString(investimento);

        mockMvc.perform(MockMvcRequestBuilders.put("/investimentos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }





}
