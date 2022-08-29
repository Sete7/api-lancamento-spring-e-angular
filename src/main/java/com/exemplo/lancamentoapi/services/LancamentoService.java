package com.exemplo.lancamentoapi.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exemplo.lancamentoapi.model.Lancamento;
import com.exemplo.lancamentoapi.model.Pessoa;
import com.exemplo.lancamentoapi.repository.LancamentoRepository;
import com.exemplo.lancamentoapi.repository.PessoaRepository;
import com.exemplo.lancamentoapi.services.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Lancamento salvar(@Valid Lancamento lancamento) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());
		if(pessoa == null || pessoa.get().isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		return lancamentoRepository.save(lancamento);
	}
	
	
	
	
}
