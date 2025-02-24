package com.mont.decor.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Usuario")
@Data
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "identificador")
    private Long identificador;
    
    @Column(nullable = false, length = 100)
    private String nome;
    
    @Column(nullable = false, length = 255)
    private String senha;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "UsuarioPerfil",
        joinColumns = @JoinColumn(name = "identificadorUsuario"),
        inverseJoinColumns = @JoinColumn(name = "identificadorPerfil")
    )
    private Set<Perfil> perfis = new HashSet<>();
}
