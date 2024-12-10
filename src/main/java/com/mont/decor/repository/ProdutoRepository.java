package com.mont.decor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mont.decor.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
