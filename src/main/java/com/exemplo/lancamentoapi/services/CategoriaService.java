package com.exemplo.lancamentoapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exemplo.lancamentoapi.model.Categoria;
import com.exemplo.lancamentoapi.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;

	public List<Categoria> listar() {
		return repository.findAll();
	}

	@Transactional
	public Categoria salvar(Categoria obj) {
		return repository.save(obj);
	}

	public Optional<Categoria> buscarPeloCodigo(Long codigo) {
		// TODO Auto-generated method stub
		return repository.findById(codigo);
	}




}
