package com.mont.decor.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mont.decor.dto.ProdutoDTO;
import com.mont.decor.model.Imagem;
import com.mont.decor.model.Produto;
import com.mont.decor.repository.CategoriaRepository;
import com.mont.decor.repository.ProdutoRepository;
import com.mont.decor.service.s3.S3Service;

@Service
public class ProdutoServiceImpl implements ProdutoService{
	
	@Autowired
    private S3Service s3Service;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Override
	public Produto cadastrarProduto(ProdutoDTO produtoDTO, List<MultipartFile> imagens) {
		Produto produto = new Produto();
		produto.setCategoria(categoriaRepository.findById(produtoDTO.idCategoria()).get());
		produto.setDescricao(produtoDTO.descricao());
		produto.setNome(produtoDTO.nome());
		produto.setPreco(produtoDTO.preco());
		produto.setImagens(new ArrayList<>());
		
		for (MultipartFile imagem : imagens) {
			Imagem imageModel = new Imagem();
			imageModel.setProduto(produto);
			imageModel.setUrl(s3Service.uploadFile(imagem));
			produto.getImagens().add(imageModel);
		}
		return produtoRepository.save(produto);
	}
	
	@Override
	public List<Produto> getProdutos() {
		return produtoRepository.findAll();
	}
}
