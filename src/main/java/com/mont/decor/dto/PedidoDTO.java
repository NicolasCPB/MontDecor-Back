package com.mont.decor.dto;

import java.util.Date;
import java.util.List;

public record PedidoDTO(String nomeUsuario, String numeroUsuario, Date dataAluguel, List<ItemPedidoDTO> itensPedidos) {}
