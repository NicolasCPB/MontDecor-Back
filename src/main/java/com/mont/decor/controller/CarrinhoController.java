package com.mont.decor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mont.decor.dto.PedidoDTO;
import com.mont.decor.service.CarrinhoService;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

	@Autowired
	private CarrinhoService service;
	
	@PostMapping("/finalizarPedido")
    public HttpEntity<Void> finalizarPedido(
            @RequestBody PedidoDTO carrinho) {
		service.finalizarPedido(carrinho);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
