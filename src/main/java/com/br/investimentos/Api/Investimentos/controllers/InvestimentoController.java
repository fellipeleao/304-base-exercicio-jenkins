package com.br.investimentos.Api.Investimentos.controllers;

import com.br.investimentos.Api.Investimentos.models.Investimento;
import com.br.investimentos.Api.Investimentos.services.InvestimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/investimentos")
public class InvestimentoController {

    @Autowired
    private InvestimentoService investimentoService;

    @GetMapping
    public ResponseEntity<Iterable<Investimento>> buscarTodos() {
        try {
            return ResponseEntity.status(200).body(investimentoService.buscarTodos());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Investimento> buscarPorId(@PathVariable Integer id) {
        try {
            Optional<Investimento> optional = investimentoService.buscarPorId(id);
            return ResponseEntity.status(200).body(optional.get());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Investimento> salvar(@RequestBody @Valid Investimento investimento) {
        try {
            Investimento retorno = investimentoService.salvar(investimento);
            return ResponseEntity.status(201).body(retorno);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Investimento> deletar(@PathVariable Integer id) {
        try {
            Optional<Investimento> optional = investimentoService.buscarPorId(id);
            investimentoService.deletar(optional.get());
            return ResponseEntity.status(200).body(optional.get());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Investimento> atualizar(@PathVariable Integer id, @RequestBody Investimento investimento) {
        try {
            investimento.setId(id);
            return ResponseEntity.status(200).body(investimentoService.atualizar(investimento));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }


}
