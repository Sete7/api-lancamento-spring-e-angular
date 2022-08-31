package com.exemplo.lancamentoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemplo.lancamentoapi.model.Lancamento;
import com.exemplo.lancamentoapi.repository.lancamento.LancamentoRepositoryQuery;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRepositoryQuery {

}
