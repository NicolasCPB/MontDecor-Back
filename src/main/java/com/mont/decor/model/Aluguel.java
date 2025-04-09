package com.mont.decor.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tab_aluguel")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "identificador")
    private Long identificador;

    @ManyToOne
    @JoinColumn(name = "identificadorUsuario", nullable = false)
    private Usuario usuario;

    @Column(name = "dataAluguel", nullable = false)
    private Date dataAluguel;

    @OneToMany(mappedBy = "aluguel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemAluguel> itens = new ArrayList<>();
    
    public void adicionarItem(ItemAluguel item) {
        itens.add(item);
        item.setAluguel(this);
    }
}
