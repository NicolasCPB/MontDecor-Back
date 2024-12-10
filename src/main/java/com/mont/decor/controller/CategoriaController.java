package com.mont.decor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mont.decor.model.Categoria;
import com.mont.decor.service.CategoriaService;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
	
    @Autowired
    private CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria categoria) {
        return new ResponseEntity<Categoria>(categoriaService.salvarCategoria(categoria), HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<Categoria>> getCategorias() {
    	return new ResponseEntity<List<Categoria>>(categoriaService.getCategorias(), HttpStatus.OK);
    }
}
