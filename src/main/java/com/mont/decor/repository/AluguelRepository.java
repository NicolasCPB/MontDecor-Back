package com.mont.decor.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mont.decor.model.Aluguel;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Long> {
	@Query("SELECT DISTINCT a.dataAluguel FROM Aluguel a "
			+ "JOIN a.itens i "
			+ "JOIN i.produto p " 
			+ "WHERE a.dataAluguel >= :data AND p.identificador = :identificadorProduto "
			+ "ORDER BY a.dataAluguel asc")
    List<Date> findByDataAluguelGreaterThanAndItensProdutoIdentificador(
            @Param("data") Date data, 
            @Param("identificadorProduto") Long identificadorProduto);
	
	@Query("SELECT DISTINCT a.dataAluguel FROM Aluguel a "
			+ "JOIN a.itens i "
			+ "JOIN i.produto p "
			+ "JOIN p.categoria c "
			+ "WHERE a.dataAluguel >= :data AND c.identificador = :identificadorCategoria "
			+ "ORDER BY a.dataAluguel asc")
	List<Date> findAllAlugueisByDataAndCategoriaIdentificador(
            @Param("data") Date data, 
            @Param("identificadorCategoria") Long identificadorCategoria);
	
	List<Aluguel> findByDataAluguel(Date dataAluguel);
}
