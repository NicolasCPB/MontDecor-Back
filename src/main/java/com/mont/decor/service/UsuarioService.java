package com.mont.decor.service;

import com.mont.decor.dto.UsuarioDTO;
import com.mont.decor.model.Usuario;

public interface UsuarioService {

	Usuario criarUsuario(UsuarioDTO dto);

	Usuario getUsuario(String usuario);

	String login(String usuario, String senha);

	String editarUsuario(UsuarioDTO usuarioDTO);

}
