package com.mont.decor.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mont.decor.dto.CarrinhoDTO;
import com.mont.decor.dto.PedidoDTO;
import com.mont.decor.exceptions.ProdutoIndisponivelException;
import com.mont.decor.model.Aluguel;
import com.mont.decor.model.Categoria;
import com.mont.decor.model.ItemAluguel;
import com.mont.decor.model.Produto;
import com.mont.decor.model.Usuario;
import com.mont.decor.repository.AluguelRepository;
import com.mont.decor.repository.ProdutoRepository;
import com.mont.decor.repository.UsuarioRepository;
import com.mont.decor.service.twilio.WhatsAppService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarrinhoServiceImpl implements CarrinhoService{

	private final LogService logService;
	
	private final WhatsAppService whatsAppService;
	
	private final AluguelRepository aluguelRepository;
	
	private final UsuarioRepository usuarioRepository;
	
	private final ProdutoRepository produtoRepository;
	
	private static final String PREFIXO_BRASIL = "55";
	
	private static final BigDecimal PERCENTUAL_ENTRADA = new BigDecimal(0.3);
	
	private static final String CHAVE_PIX = "gabrenascimento04@gmail.com";
	
	private static final long QUANTIDADE_CILINDROS_NO_ESTOQUE = 2;
	
	@Value("${email.user}")
	private String destinatario; 
	
	@Override
	public void finalizarPedido(PedidoDTO pedido) {
		validarAluguel(pedido);
		String numeroComPrefixo = PREFIXO_BRASIL + pedido.numeroUsuario(); 
		try {
//			whatsAppService.enviarMensagem(montaMensagem(pedido), numeroComPrefixo);
			salvarAluguel(pedido);
		} catch (Exception e) {
			e.printStackTrace();
			logService.salvarLog(new Date(), "Ocorreu um erro ao realizar o pedido.", e.getMessage());
			throw new RuntimeException("Ocorreu um erro ao realizar o pedido.", e);
		}
		return;
	}
	
	public void validarAluguel(PedidoDTO pedidoDTO) {
	    List<Aluguel> alugueisNaData = aluguelRepository.findByDataAluguel(pedidoDTO.dataAluguel());
	    
	    List<String> produtosIndisponiveis = new ArrayList<>();
	    
	    long quantidadeKitsNaData = alugueisNaData.stream()
	            .flatMap(aluguel -> aluguel.getItens().stream())
	            .filter(itemAluguel -> itemAluguel.getProduto().getCategoria().getIdentificador().equals(Categoria.KITS))
	            .count();
	    
	    Set<Long> produtosAlugadosIds = alugueisNaData.stream()
	            .flatMap(aluguel -> aluguel.getItens().stream())
	            .map(item -> item.getProduto().getIdentificador())
	            .collect(Collectors.toSet());
	    
	    for (CarrinhoDTO carrinho : pedidoDTO.carrinho()) {
	        Produto produtoPedido = produtoRepository.findById(carrinho.produto().getIdentificador()).get();
	        Long produtoId = produtoPedido.getIdentificador();
	        String produtoNome = produtoPedido.getNome();
	        
	        if (produtoPedido.getCategoria().getIdentificador().equals(Categoria.KITS) 
	                && quantidadeKitsNaData >= QUANTIDADE_CILINDROS_NO_ESTOQUE) {
	            produtosIndisponiveis.add(produtoNome);
	        } 
	        else if (produtosAlugadosIds.contains(produtoId)) {
	            produtosIndisponiveis.add(produtoNome);
	        }
	    }
	    
	    if (!produtosIndisponiveis.isEmpty()) {
	        StringBuilder mensagem = new StringBuilder();
	        for (String produto : produtosIndisponiveis) {
	            mensagem.append(String.format("O produto %s não está disponível nesta data\n", produto));
	        }
	        throw new ProdutoIndisponivelException(mensagem.toString());
	    }
	}
	
	private String montaMensagem(PedidoDTO pedido) {
	    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	    StringBuilder corpoMensagem = new StringBuilder();
	    BigDecimal valorTotalPedido = BigDecimal.ZERO;

	    corpoMensagem.append("Olá, ").append(pedido.nomeUsuario()).append("! 😊\n\n");
	    corpoMensagem.append("Seu pedido está quase confirmado! Aqui estão os detalhes:\n\n");
	    corpoMensagem.append("📅 Data do aluguel: ").append(formato.format(pedido.dataAluguel())).append("\n");
	    corpoMensagem.append("📞 Contato: ").append(pedido.numeroUsuario()).append("\n\n");
	    corpoMensagem.append("Itens reservados:\n");

	    for (CarrinhoDTO carrinho : pedido.carrinho()) {
	        String nome = carrinho.produto().getNome();
	        Integer quantidade = carrinho.quantidade();
	        BigDecimal valorUnitario = carrinho.produto().getPreco();
	        BigDecimal valorTotalItem = valorUnitario.multiply(new BigDecimal(quantidade));
	        valorTotalPedido = valorTotalPedido.add(valorTotalItem);

	        corpoMensagem.append("• ").append(quantidade).append("x ").append(nome)
	            .append(" → R$ ").append(valorTotalItem.setScale(2, RoundingMode.HALF_UP)).append("\n");
	    }

	    corpoMensagem.append("\n💰 Total: R$ ").append(valorTotalPedido.setScale(2, RoundingMode.HALF_UP)).append("\n\n");

	    BigDecimal valorEntrada = valorTotalPedido.multiply(PERCENTUAL_ENTRADA).setScale(2, RoundingMode.HALF_UP);
	    BigDecimal valorRestante = valorTotalPedido.subtract(valorEntrada).setScale(2, RoundingMode.HALF_UP);

	    corpoMensagem.append("Para confirmar sua reserva, é necessário realizar o pagamento de 30% (R$ ")
	        .append(valorEntrada).append(") via PIX para a chave: ").append(CHAVE_PIX).append(".\n");
	    corpoMensagem.append("O valor restante (R$ ").append(valorRestante).append(") pode ser pago no dia da retirada.\n\n");

	    corpoMensagem.append("Estamos muito felizes por fazer parte desse momento especial! Qualquer dúvida, é só chamar.\n");
	    corpoMensagem.append("Desejamos um evento maravilhoso! 🎉\n\n");
	    corpoMensagem.append("Com carinho,\nEquipe MontDecor 💖");

	    return corpoMensagem.toString();
	}

	private void salvarAluguel(PedidoDTO pedido) {
        try {
            Usuario usuario = usuarioRepository.findByNome(pedido.nomeUsuario()).orElse(null);
            if (Objects.isNull(usuario)) {
                usuario = new Usuario();
                usuario.setNome(pedido.nomeUsuario());
                usuario.setTelefone(pedido.numeroUsuario());
                usuario.setSenha(gerarSenhaTemporaria());
                usuarioRepository.save(usuario);
            }
            
            Aluguel aluguel = new Aluguel();
            aluguel.setUsuario(usuario);
            aluguel.setDataAluguel(pedido.dataAluguel());
            
            aluguelRepository.save(aluguel);
            
            for (CarrinhoDTO item : pedido.carrinho()) {
                ItemAluguel itemAluguel = new ItemAluguel();
                itemAluguel.setAluguel(aluguel);
                itemAluguel.setProduto(item.produto());
                itemAluguel.setQuantidade(item.quantidade());
                
                aluguel.adicionarItem(itemAluguel);
            }
            
            aluguelRepository.save(aluguel);
        } catch (Exception e) {
            logService.salvarLog(new Date(), "Erro ao salvar aluguel no banco de dados", e.getMessage());
            throw new RuntimeException("Erro ao salvar aluguel no banco de dados", e);
        }
    }
    
    private String gerarSenhaTemporaria() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
	
}
