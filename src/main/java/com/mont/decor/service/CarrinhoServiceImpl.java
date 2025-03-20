package com.mont.decor.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mont.decor.dto.CarrinhoDTO;
import com.mont.decor.dto.PedidoDTO;
import com.mont.decor.service.email.EmailService;
import com.mont.decor.service.twilio.TwilioService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarrinhoServiceImpl implements CarrinhoService{

	private final EmailService emailService;
	
	private final LogService logService;
	
	private final TwilioService twilioService;
	
	@Value("${email.user}")
	private String destinatario; 
	
	@Override
	public void finalizarPedido(PedidoDTO pedido) {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		StringBuilder corpoMensagem = new StringBuilder();
		BigDecimal valorTotalPedido = BigDecimal.ZERO;
		corpoMensagem.append("Nome cliente: " + pedido.nomeUsuario() + "\n");
		corpoMensagem.append("Número cliente: " + pedido.numeroUsuario() + "\n");
		corpoMensagem.append("Data do aluguel: " + formato.format(pedido.dataAluguel()) + "\n");
		for (CarrinhoDTO carrinho : pedido.carrinho()) {
			String nome = carrinho.produto().getNome();
			Integer quantidade = carrinho.quantidade();
			BigDecimal valorUnitario = carrinho.produto().getPreco();
			BigDecimal valorTotalItem = valorUnitario.multiply(new BigDecimal(quantidade));
			corpoMensagem.append("Item: " + nome + " | " + "Quantidade: " + quantidade + " | " + "Valor unitário: R$ " + valorUnitario.toString() + " | " + "Valor total item: R$ " + valorTotalItem + "\n");
			
			valorTotalPedido = valorTotalPedido.add(valorTotalItem);
		}
		corpoMensagem.append("\n" + "Valor total do pedido: R$ " + valorTotalPedido.toString());
		try {
			emailService.sendEmail(destinatario, "Aluguel MontDecor", corpoMensagem.toString());
			twilioService.enviarMensagemWhatsapp(corpoMensagem.toString());
		} catch (MessagingException e) {
			e.printStackTrace();
			logService.salvarLog(new Date(), "Ocorreu um erro ao realizar o pedido.", e.getMessage());
			throw new RuntimeException("Ocorreu um erro ao realizar o pedido.", e);
		}
		return;
	}
	
}
