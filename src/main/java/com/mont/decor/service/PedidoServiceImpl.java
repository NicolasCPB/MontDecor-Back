package com.mont.decor.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mont.decor.dto.ItemPedidoDTO;
import com.mont.decor.dto.PedidoDTO;
import com.mont.decor.enums.SituacaoProcessamentoEnum;
import com.mont.decor.model.ItemPedido;
import com.mont.decor.model.Pedido;
import com.mont.decor.model.ProcessamentoPedido;
import com.mont.decor.repository.ProcessamentoPedidoRepository;
import com.mont.decor.service.rabbitmq.RabbitMQService;

@Service
public class PedidoServiceImpl implements PedidoService{

	@Autowired
	private RabbitMQService rabbitService;
	
	@Autowired
	private ProcessamentoPedidoRepository processamentoPedidoRepository;
	
	@Override
	public void finalizarPedido(PedidoDTO pedido) {
		ProcessamentoPedido processamentoPedido = new ProcessamentoPedido();
		processamentoPedido.setDataRecebimento(new Date());
		processamentoPedido.setPedido(converterParaModel(pedido));
		processamentoPedido.setIdentificadorTAB_SituacaoProcessamento(SituacaoProcessamentoEnum.RECEBIDO.getIdentificador());
		processamentoPedidoRepository.save(processamentoPedido);
		
		rabbitService.enviarParaFila(processamentoPedido);
	}

	/* Jogar isso aqui no consumidor
	 * @Value("${email.user}")
	private String destinatario; 
	 * SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
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
			return;
		} catch (MessagingException e) {
			e.printStackTrace();
			logService.salvarLog(new Date(), "Ocorreu um erro ao realizar o pedido.", e.getMessage());
			throw new RuntimeException("Ocorreu um erro ao realizar o pedido.", e);
		}
	 */
	
	private Pedido converterParaModel(PedidoDTO pedidoDTO) {
		Pedido pedido = new Pedido();
		pedido.setDataAluguel(pedidoDTO.dataAluguel());
		pedido.setNomeUsuario(pedidoDTO.nomeUsuario());
		pedido.setNumeroUsuario(pedidoDTO.numeroUsuario());

		List<ItemPedido> itens = new ArrayList<ItemPedido>();
		for (ItemPedidoDTO itemDTO : pedidoDTO.itensPedidos()) {
			ItemPedido item = new ItemPedido();
			
			item.setQuantidade(itemDTO.quantidade());
			item.setProduto(itemDTO.produto());
			
			itens.add(item);
		}
		
		pedido.setItensPedidos(itens);
		return pedido;
	}
}
