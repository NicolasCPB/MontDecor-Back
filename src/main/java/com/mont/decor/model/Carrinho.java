package com.mont.decor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "TAB_Carrinho")
@Data
public class Carrinho {
	
	@OneToOne
    @JoinColumn(name = "identificadorTAB_Produto", nullable = false)
	private Produto produto;
	
	private Integer quantidade;
}
