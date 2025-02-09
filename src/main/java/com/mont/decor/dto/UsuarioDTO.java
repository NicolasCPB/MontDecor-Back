package com.mont.decor.dto;

import java.util.List;

public record UsuarioDTO(
	    String nome,
	    String senha,
	    List<Long> perfisIds
	) {}
