package com.br.investimentos.Api.Investimentos.services;

import com.br.investimentos.Api.Investimentos.enums.RiscoInvestimento;
import com.br.investimentos.Api.Investimentos.models.Investimento;
import com.br.investimentos.Api.Investimentos.repositories.InvestimentoRepository;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class InvestimentoServiceTest {


    @MockBean
    InvestimentoRepository investimentoRepository;

    @Autowired
    InvestimentoService investimentoService;

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
    public void deveBuscarTodos() {
        investimento.setId(1);
        Mockito.when(investimentoRepository.findAll()).thenReturn(Arrays.asList(investimento));

        Iterable<Investimento> iterableRetorno = investimentoService.buscarTodos();
        List<Investimento> listRetorno = (List) iterableRetorno;

        Assertions.assertEquals(1, listRetorno.size());
        Assertions.assertEquals(1, listRetorno.get(0).getId());
        Assertions.assertEquals("Itau", listRetorno.get(0).getNomeInvestimento());
    }

    @Test
    public void deveBuscaTodosComListaVazia() {
        Mockito.when(investimentoRepository.findAll()).thenReturn(Arrays.asList());

        Iterable<Investimento> iterableRetorno = investimentoService.buscarTodos();
        List<Investimento> listRetorno = (List) iterableRetorno;

        Assertions.assertEquals(0, listRetorno.size());
    }

    @Test
    public void deveBuscarPorId() {
        investimento.setId(1);
        Mockito.when(investimentoRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(investimento));
        Optional<Investimento> optionalRetorno = investimentoService.buscarPorId(1);

        Assertions.assertEquals(1, optionalRetorno.get().getId());
        Assertions.assertEquals("Itau", optionalRetorno.get().getNomeInvestimento());
    }

    @Test
    public void deveDarObjectNotFoundExceptionAoBuscarPorId() {
        Mockito.when(investimentoRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> investimentoService.buscarPorId(1));
    }

    @Test
    public void deveSalvarInvestimento() {
        Mockito.when(investimentoRepository.save(Mockito.any(Investimento.class))).thenReturn(investimento);
        Investimento retorno = investimentoService.salvar(investimento);

        Assertions.assertEquals("Itau", retorno.getNomeInvestimento());
    }

    @Test
    public void deveAtualizarInvestimento() {
        Mockito.when(investimentoRepository.save(Mockito.any(Investimento.class))).thenReturn(investimento);
        Investimento retorno = investimentoService.atualizar(investimento);

        Assertions.assertEquals("Itau", retorno.getNomeInvestimento());
    }

    @Test
    public void deveDeletarInvestimento() {

        investimentoService.deletar(investimento);

        Mockito.verify(investimentoRepository, Mockito.times(1)).delete(investimento);
    }


}
