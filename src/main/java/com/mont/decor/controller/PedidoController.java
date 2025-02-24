package com.mont.decor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mont.decor.dto.PedidoDTO;
import com.mont.decor.service.PedidoService;

@RestController
@RequestMapping("/carrinho")
public class PedidoController {

	@Autowired
	private PedidoService service;
	
	@PostMapping("/finalizarPedido")
    public void finalizarPedido(
            @RequestBody PedidoDTO carrinho) {
		service.finalizarPedido(carrinho);
        return;
    }
}
