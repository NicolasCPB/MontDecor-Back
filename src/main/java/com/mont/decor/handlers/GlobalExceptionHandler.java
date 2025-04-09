package com.mont.decor.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mont.decor.exceptions.ProdutoIndisponivelException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProdutoIndisponivelException.class)
    public ResponseEntity<String> handleProdutoIndisponivelException(ProdutoIndisponivelException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
