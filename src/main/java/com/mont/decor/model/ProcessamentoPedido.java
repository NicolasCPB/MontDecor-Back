package com.mont.decor.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "ProcessamentoPedido")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessamentoPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "identificador")
    private Long identificador;
    
    private Integer identificadorTAB_SituacaoProcessamento;
    
    @OneToOne
    @JoinColumn(name = "identificadorTAB_Pedido", nullable = true)
    private Pedido pedido;
    
    private Date dataRecebimento;
    
    private Date dataInicioProcessamento;
    
    private Date dataFimProcessamento;
}
