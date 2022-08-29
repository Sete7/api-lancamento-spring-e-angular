package com.exemplo.lancamentoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemplo.lancamentoapi.model.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {

}
