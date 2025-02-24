package com.mont.decor.service.rabbitmq;

import java.util.Date;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mont.decor.model.ProcessamentoPedido;
import com.mont.decor.service.LogService;

@Service
public class RabbitMQServiceImpl implements RabbitMQService {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private LogService logService;
	
	@Value("${exchange}")
	private String exchange; 
	
	@Override
	public void enviarParaFila(ProcessamentoPedido processamentoPedido) {
		try {
			rabbitTemplate.convertAndSend(exchange, "", processamentoPedido);
		} catch (AmqpException e) {
			logService.salvarLog(new Date(), "Erro ao enviar pedido para a fila", e.getMessage());
			throw new RuntimeException("Erro ao enviar pedido para fila");
		}
	}
}
