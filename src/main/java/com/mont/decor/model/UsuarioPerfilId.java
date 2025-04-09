package com.mont.decor.model;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class UsuarioPerfilId implements Serializable {
	private static final long serialVersionUID = 895704737729484503L;
	private Long identificadorUsuario;
    private Long identificadorPerfil;
}
