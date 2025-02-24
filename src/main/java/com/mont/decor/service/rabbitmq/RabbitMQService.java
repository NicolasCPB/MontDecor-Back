package com.mont.decor.service.rabbitmq;

import com.mont.decor.model.ProcessamentoPedido;

public interface RabbitMQService {

	void enviarParaFila(ProcessamentoPedido processamentoPedido);

}
