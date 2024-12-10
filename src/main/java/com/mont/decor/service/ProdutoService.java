package com.mont.decor.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mont.decor.dto.ProdutoDTO;
import com.mont.decor.model.Produto;

public interface ProdutoService {
	Produto cadastrarProduto(ProdutoDTO produto, List<MultipartFile> imagens);

	List<Produto> getProdutos();
}
