package com.mont.decor.dto;

import java.math.BigDecimal;

public record ItemAluguelDTO(
	String nomeProduto,
	Integer quantidade,
	BigDecimal valor
) {}
