package com.mont.decor.exceptions;

public class ProdutoIndisponivelException extends RuntimeException {
	private static final long serialVersionUID = -2100628754911675601L;

	public ProdutoIndisponivelException(String message) {
        super(message);
    }
}
