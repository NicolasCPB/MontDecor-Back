package com.mont.decor.controller;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mont.decor.service.AluguelService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/aluguel")
@RequiredArgsConstructor
public class AluguelController {

	private final AluguelService aluguelService;
	
	@GetMapping("/getAlugueisPendentesPorProduto/{identificadorProduto}")
    public ResponseEntity<List<Date>> getAlugueisPendentesPorProduto(@PathVariable(name = "identificadorProduto") Long identificadorProduto) {
    	return new ResponseEntity<List<Date>>(aluguelService.getAlugueisPendentesPorProduto(identificadorProduto), HttpStatus.OK);
    }
}
