package com.mont.decor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mont.decor.dto.UsuarioDTO;
import com.mont.decor.model.Usuario;
import com.mont.decor.service.UsuarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UsuarioController {
	
	private final UsuarioService usuarioService;
	
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam(value = "usuario") String usuario, 
                                                     @RequestHeader(value = "senha") String senha) {
        return new ResponseEntity<String>(usuarioService.login(usuario, senha), HttpStatus.OK);
    }

    
    @PostMapping("/register")
    public ResponseEntity<Usuario> criarUsuario(@RequestBody UsuarioDTO dto) {
        try {
            Usuario novoUsuario = usuarioService.criarUsuario(dto);
            return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PutMapping("/editarUsuario")
    public ResponseEntity<String> editarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
    	return new ResponseEntity<String>(usuarioService.editarUsuario(usuarioDTO), HttpStatus.OK);
    }
}