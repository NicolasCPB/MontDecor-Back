package com.mont.decor.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "UsuarioPerfil")
@Data
public class UsuarioPerfil {
    @EmbeddedId
    private UsuarioPerfilId id;
    
    @ManyToOne
    @MapsId("identificadorUsuario")
    @JoinColumn(name = "identificadorUsuario")
    private Usuario usuario;
    
    @ManyToOne
    @MapsId("identificadorPerfil")
    @JoinColumn(name = "identificadorPerfil")
    private Perfil perfil;
}