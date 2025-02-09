package com.mont.decor.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class UsuarioPerfilId implements Serializable {
    private Long identificadorUsuario;
    private Long identificadorPerfil;
}
