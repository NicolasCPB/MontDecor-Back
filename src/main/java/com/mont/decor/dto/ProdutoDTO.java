package com.mont.decor.dto;

import java.math.BigDecimal;

public record ProdutoDTO(String nome, String descricao, BigDecimal preco, Integer quantidade, Long idCategoria) {

}
