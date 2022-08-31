package com.exemplo.lancamentoapi.repository.lancamento;

import java.util.List;

import com.exemplo.lancamentoapi.model.Lancamento;
import com.exemplo.lancamentoapi.repository.filter.LancamentoFiter;

public interface LancamentoRepositoryQuery {

	List<Lancamento> filtrar(LancamentoFiter lancamentoFiter);
	
}
