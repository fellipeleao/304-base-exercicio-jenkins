package com.br.investimentos.Api.Investimentos.repositories;

import com.br.investimentos.Api.Investimentos.models.Investimento;
import org.springframework.data.repository.CrudRepository;

public interface InvestimentoRepository extends CrudRepository<Investimento, Integer> {
}
