package com.mont.decor.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "TAB_Pedido")
@Data
public class Pedido {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "identificador")
	private Long identificador;
	
	private String nomeUsuario;
	
	private String numeroUsuario;
	
	private Date dataAluguel;
	
	@ManyToOne
    @JoinColumn(name = "identificadorTAB_Carrinho", nullable = false)
    private List<Carrinho> carrinhos;
}
