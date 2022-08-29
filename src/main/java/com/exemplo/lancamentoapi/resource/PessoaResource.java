package com.exemplo.lancamentoapi.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.lancamentoapi.event.RecursoCriadoEvent;
import com.exemplo.lancamentoapi.model.Pessoa;
import com.exemplo.lancamentoapi.repository.PessoaRepository;
import com.exemplo.lancamentoapi.services.PessoaService;

@RestController
@RequestMapping(value = "pessoa")
public class PessoaResource {

	@Autowired
	private PessoaService service;

	@Autowired
	private PessoaRepository repository;

	@Autowired
	private ApplicationEventPublisher publisher;

	@PostMapping(value = "/salvar")
	public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa obj = service.salvar(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, obj.getCodigo()));
		return new ResponseEntity<>(obj, HttpStatus.CREATED);
	}

	@GetMapping
	public List<Pessoa> listar() {
		return service.listar();
	}

	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Pessoa> pessoa = service.buscarPeloCodigo(codigo);
		return pessoa.isPresent() ? ResponseEntity.ok(pessoa.get()) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remove(@PathVariable Long codigo) {
		repository.deleteById(codigo);
	}

	@PutMapping(value = "/{codigo}")
	public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @RequestBody Pessoa pessoa) {
		Pessoa pessoaSalva = service.atualizar(codigo, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	}

	@PutMapping(value = "/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarPropriedadeAtivo(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		service.atualizarPropriedadeAtivo(codigo, ativo);
	}

}
