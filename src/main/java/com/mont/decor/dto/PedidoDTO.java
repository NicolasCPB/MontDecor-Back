package com.mont.decor.dto;

import java.util.Date;
import java.util.List;

public record PedidoDTO(String numeroUsuario, Date dataAluguel, List<CarrinhoDTO> carrinho) {}
