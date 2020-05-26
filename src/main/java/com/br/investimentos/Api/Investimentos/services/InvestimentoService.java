package com.br.investimentos.Api.Investimentos.services;

import com.br.investimentos.Api.Investimentos.models.Investimento;
import com.br.investimentos.Api.Investimentos.repositories.InvestimentoRepository;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvestimentoService {

    @Autowired
    InvestimentoRepository investimentoRepository;

    public Optional<Investimento> buscarPorId(int id) {
        Optional<Investimento> optional = investimentoRepository.findById(id);
        if (optional.isPresent()) {
            return investimentoRepository.findById(id);
        } else {
            throw new ObjectNotFoundException(InvestimentoService.class, "Id do Investimento não localizado!");
        }
    }

    public Investimento salvar(Investimento investimento) {
        return investimentoRepository.save(investimento);
    }

    public Iterable<Investimento> buscarTodos() {
        return investimentoRepository.findAll();
    }

    public Investimento atualizar(Investimento investimento) {
        return investimentoRepository.save(investimento);
    }

    public void deletar(Investimento investimento) {
        investimentoRepository.delete(investimento);
    }

}
