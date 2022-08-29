package com.exemplo.lancamentoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemplo.lancamentoapi.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

}
