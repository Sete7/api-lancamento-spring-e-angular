package com.exemplo.lancamentoapi.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.exemplo.lancamentoapi.model.Lancamento;
import com.exemplo.lancamentoapi.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	Page<Lancamento> filtrar(LancamentoFilter lancamentoFiter, Pageable pageable);
	
}
