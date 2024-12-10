package com.mont.decor.dto;

import java.math.BigDecimal;

public record ProdutoDTO(String nome, String descricao, BigDecimal preco, Long idCategoria) {

}
