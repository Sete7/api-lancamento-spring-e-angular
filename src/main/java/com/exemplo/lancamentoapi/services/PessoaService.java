package com.exemplo.lancamentoapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exemplo.lancamentoapi.model.Pessoa;
import com.exemplo.lancamentoapi.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;

	@Transactional
	public Pessoa salvar(Pessoa pessoa) {
		Pessoa obj = repository.save(pessoa);
		return obj;
	}

	public List<Pessoa> listar() {
		return repository.findAll();
	}

	public Optional<Pessoa> buscarPeloCodigo(Long codigo) {
		Optional<Pessoa> pessoa = repository.findById(codigo);
		return pessoa;

	}

	public Pessoa atualizar(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		return repository.save(pessoaSalva);
	}

	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscarPessoaPeloCodigo(codigo);
		pessoaSalva.setAtivo(ativo);
		repository.save(pessoaSalva);
	}

	private Pessoa buscarPessoaPeloCodigo(Long codigo) {
		Pessoa pessoaSalva = repository.findById(codigo).orElseThrow(() -> new EmptyResultDataAccessException(1));
		if (pessoaSalva == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}

}
