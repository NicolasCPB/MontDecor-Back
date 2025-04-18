package com.mont.decor.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tab_categoria")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {
	
	public static final Long KITS = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "identificador")
    private Long identificador;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;
}
