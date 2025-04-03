package com.mont.decor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mont.decor.dto.ProdutoDTO;
import com.mont.decor.model.Categoria;
import com.mont.decor.model.Imagem;
import com.mont.decor.model.Produto;
import com.mont.decor.repository.CategoriaRepository;
import com.mont.decor.repository.ImagemRepository;
import com.mont.decor.repository.ProdutoRepository;
import com.mont.decor.service.s3.S3Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService{
	
    private final S3Service s3Service;
	
	private final ProdutoRepository produtoRepository;
	
	private final CategoriaRepository categoriaRepository;
	
	private final ImagemRepository imagemRepository;
	
	@Override
	public Produto cadastrarProduto(ProdutoDTO produtoDTO, List<MultipartFile> imagens) {
		Produto produto = new Produto();
		produto.setCategoria(categoriaRepository.findById(produtoDTO.idCategoria()).get());
		produto.setDescricao(produtoDTO.descricao());
		produto.setNome(produtoDTO.nome());
		produto.setPreco(produtoDTO.preco());
		produto.setQuantidade(produtoDTO.quantidade());
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
		return produtoRepository.findAll().stream()
		        .sorted((p1, p2) -> {
		            boolean isKitP1 = p1.getCategoria().getIdentificador().equals(Categoria.KITS);
		            boolean isKitP2 = p2.getCategoria().getIdentificador().equals(Categoria.KITS);

		            if (isKitP1 && !isKitP2) return -1;
		            if (!isKitP1 && isKitP2) return 1;
		            return 0;
		        })
		        .collect(Collectors.toList());
	}

	@Override
	public Produto getProdutoByIdentificador(Long identificador) {
		return produtoRepository.findById(identificador).get();
	}
	
	@Override
	@Transactional
	public void deleteByIdentificador(Long identificador) {
		List<Imagem> imagensProduto = imagemRepository.findByProdutoIdentificador(identificador);
		List<Long> idsImagens = imagensProduto.stream().map(Imagem::getIdentificador).collect(Collectors.toList());
		imagemRepository.deleteAllById(idsImagens);
		
		produtoRepository.deleteById(identificador);
	}
	
	@Override
	@Transactional
	public void editarProduto(Produto produto, List<MultipartFile> imagens) {
		if (ObjectUtils.isEmpty(produto.getImagens())) {
			imagemRepository.deleteAllByProdutoIdentificador(produto.getIdentificador());
		} else {
			for (Imagem imagem : produto.getImagens()) {
				imagem.setProduto(produto);
			}
		}
		if (!ObjectUtils.isEmpty(imagens)) {			
			for (MultipartFile imagem : imagens) {
				Imagem imageModel = new Imagem();
				imageModel.setProduto(produto);
				imageModel.setUrl(s3Service.uploadFile(imagem));
				produto.getImagens().add(imageModel);
			}
		}
		produtoRepository.save(produto);
	}
}
