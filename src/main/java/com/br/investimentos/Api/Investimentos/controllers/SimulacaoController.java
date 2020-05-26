package com.br.investimentos.Api.Investimentos.controllers;

import com.br.investimentos.Api.Investimentos.models.SimulacaoCadastro;
import com.br.investimentos.Api.Investimentos.models.SimulacaoResposta;
import com.br.investimentos.Api.Investimentos.services.SimulacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/simulacao")
public class SimulacaoController {

    @Autowired
    private SimulacaoService simulacaoService;

    @PutMapping
    public ResponseEntity<SimulacaoResposta> realizarSimulacao(@RequestBody SimulacaoCadastro simulacaoCadastro) {
        try {
            return ResponseEntity.status(200).body(simulacaoService.realizarSimulacao(simulacaoCadastro));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

}
