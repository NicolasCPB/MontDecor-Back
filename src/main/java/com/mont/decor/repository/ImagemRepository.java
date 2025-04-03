package com.mont.decor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mont.decor.model.Imagem;

@Repository
public interface ImagemRepository extends JpaRepository<Imagem, Long>{

	List<Imagem> findByProdutoIdentificador(Long identificadorProduto);
	
	void deleteAllByProdutoIdentificador(Long identificadorProduto);
}
