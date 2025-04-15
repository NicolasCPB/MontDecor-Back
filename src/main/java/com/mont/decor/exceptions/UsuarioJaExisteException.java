package com.mont.decor.exceptions;

public class UsuarioJaExisteException extends RuntimeException {
	private static final long serialVersionUID = -2100628754911675601L;

	public UsuarioJaExisteException(String message) {
        super(message);
    }
}
