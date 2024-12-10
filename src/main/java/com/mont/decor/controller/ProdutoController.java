package com.mont.decor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mont.decor.dto.ProdutoDTO;
import com.mont.decor.model.Produto;
import com.mont.decor.service.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public HttpEntity<Produto> cadastrarProduto(
            @RequestPart("produto") ProdutoDTO produtoDTO,
            @RequestPart("imagens") List<MultipartFile> imagens) {
        return new ResponseEntity<Produto>(produtoService.cadastrarProduto(produtoDTO, imagens), HttpStatus.CREATED);
    }
    
    @GetMapping()
    public HttpEntity<List<Produto>> getProdutos(){
    	return new ResponseEntity<List<Produto>>(produtoService.getProdutos(), HttpStatus.OK);
    }
}
