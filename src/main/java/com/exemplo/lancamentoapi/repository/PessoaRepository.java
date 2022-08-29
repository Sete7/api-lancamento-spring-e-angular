package com.exemplo.lancamentoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemplo.lancamentoapi.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
