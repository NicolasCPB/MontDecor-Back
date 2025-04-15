package com.mont.decor.dto;

import java.util.List;

public record UsuarioDTO(
	    String nome,
	    String nomeAntigo,
	    String senha,
	    String telefone,
	    List<Long> perfisIds
	) {}
