package com.mont.decor.dto;

import java.util.Date;
import java.util.List;

public record AluguelDTO(
	String nomeUsuario, 
	String telefoneUsuario, 
	Date dataAluguel,
	List<ItemAluguelDTO> itens
){}
