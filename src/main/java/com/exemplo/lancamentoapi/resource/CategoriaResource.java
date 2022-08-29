package com.exemplo.lancamentoapi.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.exemplo.lancamentoapi.event.RecursoCriadoEvent;
import com.exemplo.lancamentoapi.model.Categoria;
import com.exemplo.lancamentoapi.services.CategoriaService;

@RestController
@RequestMapping(value = "/categoria")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Categoria> listar() {
		return service.listar();
	}

	@PostMapping(value = "/salvar")
	public ResponseEntity<Categoria> salvar(@Valid @RequestBody Categoria obj, HttpServletResponse response) {
		Categoria cat = service.salvar(obj);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, cat.getCodigo()));
		return new ResponseEntity<>(cat, HttpStatus.CREATED);
	}

	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
		Optional<Categoria> categoria = service.buscarPeloCodigo(codigo);
		return categoria.isPresent() ? ResponseEntity.ok(categoria.get()) : ResponseEntity.notFound().build();
	}


}
