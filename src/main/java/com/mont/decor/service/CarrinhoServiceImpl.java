package com.mont.decor.service;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mont.decor.dto.CarrinhoDTO;
import com.mont.decor.dto.PedidoDTO;
import com.mont.decor.service.email.EmailService;

import jakarta.mail.MessagingException;

@Service
public class CarrinhoServiceImpl implements CarrinhoService{

	@Autowired
	private EmailService emailService;
	
	@Value("${email.user}")
	private String destinatario; 
	
	@Override
	public void finalizarPedido(PedidoDTO pedido) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		StringBuilder corpoMensagem = new StringBuilder();
		corpoMensagem.append("Número cliente: " + pedido.numeroUsuario() + "\n");
		corpoMensagem.append("Data do aluguel: " + formato.format(pedido.dataAluguel()) + "\n");
		for (CarrinhoDTO carrinho : pedido.carrinho()) {
			String nome = carrinho.produto().getNome();
			Integer quantidade = carrinho.quantidade();
			corpoMensagem.append("Quantidade: " + quantidade + " | " + "Item: " + nome + "\n");
		}
		try {
			emailService.sendEmail(destinatario, "Aluguel MontDecor", corpoMensagem.toString());
		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException("Ocorreu um erro ao realizar o pedido.", e);
		}
		return;
	}
	
}
