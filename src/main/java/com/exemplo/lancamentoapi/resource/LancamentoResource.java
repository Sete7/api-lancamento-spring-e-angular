package com.exemplo.lancamentoapi.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.lancamentoapi.model.Lancamento;
import com.exemplo.lancamentoapi.repository.LancamentoRepository;

@RestController
@RequestMapping(value = "lancamento")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository repository;
	
	
	@GetMapping
	public List<Lancamento> listarLancamento(){
		List<Lancamento> lista = repository.findAll();
		return lista;
	}
	
	@GetMapping(value="/{codigo}")
	public ResponseEntity<Lancamento> buscarLancamentoCodigo(@PathVariable Long codigo){
		Optional<Lancamento> lancamento = repository.findById(codigo);		
		return lancamento.isPresent() ? ResponseEntity.ok(lancamento.get()) : ResponseEntity.notFound().build();
	}
	
	
}
