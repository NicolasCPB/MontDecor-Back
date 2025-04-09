package com.mont.decor.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mont.decor.model.Categoria;
import com.mont.decor.model.Produto;
import com.mont.decor.repository.AluguelRepository;
import com.mont.decor.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AluguelServiceImpl implements AluguelService {

	private final AluguelRepository aluguelRepository;
	
	private final ProdutoRepository produtoRepository;
	
	@Override
	public List<Date> getAlugueisPendentesPorProduto(Long identificadorProduto) {
		Date dataAtual = new Date();

		Produto produto = produtoRepository.findById(identificadorProduto).get();
		
		List<Date> alugueisPendentes = new ArrayList<Date>();
		if (produto.getCategoria().getIdentificador().equals(Categoria.KITS)) {
			alugueisPendentes = aluguelRepository.findAllAlugueisByDataAndCategoriaIdentificador(dataAtual, Categoria.KITS);
		} else {
			alugueisPendentes = aluguelRepository.findByDataAluguelGreaterThanAndItensProdutoIdentificador(dataAtual, identificadorProduto);
		}

        return alugueisPendentes;
	}
}
