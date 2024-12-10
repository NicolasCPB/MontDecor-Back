package com.mont.decor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mont.decor.model.Categoria;
import com.mont.decor.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService{
	
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Categoria salvarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
    
    @Override
    public List<Categoria> getCategorias() {
    	return categoriaRepository.findAll();
    }
}
