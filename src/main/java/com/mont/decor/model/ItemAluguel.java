package com.mont.decor.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tab_item_aluguel")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemAluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "identificador")
    private Long identificador;

    @ManyToOne
    @JoinColumn(name = "identificadorTAB_Aluguel", nullable = false)
    private Aluguel aluguel;

    @ManyToOne
    @JoinColumn(name = "identificadorTAB_Produto", nullable = false)
    private Produto produto;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

}
