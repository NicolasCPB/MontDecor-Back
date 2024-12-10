package com.mont.decor.service;

import java.util.List;

import com.mont.decor.model.Categoria;

public interface CategoriaService {

	Categoria salvarCategoria(Categoria categoria);

	List<Categoria> getCategorias();

}
