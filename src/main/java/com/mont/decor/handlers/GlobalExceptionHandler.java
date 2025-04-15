package com.mont.decor.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mont.decor.exceptions.ProdutoIndisponivelException;
import com.mont.decor.exceptions.UsuarioJaExisteException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProdutoIndisponivelException.class)
    public ResponseEntity<String> handleProdutoIndisponivelException(ProdutoIndisponivelException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
    
    @ExceptionHandler(UsuarioJaExisteException.class)
    public ResponseEntity<String> handleUsuarioJaExisteException(UsuarioJaExisteException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
