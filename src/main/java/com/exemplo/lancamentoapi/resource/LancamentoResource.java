package com.exemplo.lancamentoapi.resource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.lancamentoapi.event.RecursoCriadoEvent;
import com.exemplo.lancamentoapi.exceptionhandler.LancamentoExceptionHandler.Erro;
import com.exemplo.lancamentoapi.model.Lancamento;
import com.exemplo.lancamentoapi.repository.LancamentoRepository;
import com.exemplo.lancamentoapi.repository.filter.LancamentoFilter;
import com.exemplo.lancamentoapi.services.LancamentoService;
import com.exemplo.lancamentoapi.services.exception.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping(value = "lancamento")
public class LancamentoResource {

	@Autowired
	private LancamentoService lancamentoService;

	@Autowired
	private LancamentoRepository repository;

	@Autowired
	private ApplicationEventPublisher publisher;
	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFiter, Pageable pageable) {
		Page<Lancamento> lista = repository.filtrar(lancamentoFiter, pageable);
		return lista;
	}

	@GetMapping(value = "/{codigo}")
	public ResponseEntity<Lancamento> buscarLancamentoCodigo(@PathVariable Long codigo) {
		Optional<Lancamento> lancamento = repository.findById(codigo);
		return lancamento.isPresent() ? ResponseEntity.ok(lancamento.get()) : ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Lancamento> criar(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {
		Lancamento lancamentoSalvar = lancamentoService.salvar(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvar.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvar);
	}

	@ExceptionHandler({ PessoaInexistenteOuInativaException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex) {
		String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null,
				LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		return ResponseEntity.badRequest().body(erros);
	}
	
	@DeleteMapping(value="{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerLancamento(@PathVariable Long codigo) {
		repository.deleteById(codigo);
	}

}
