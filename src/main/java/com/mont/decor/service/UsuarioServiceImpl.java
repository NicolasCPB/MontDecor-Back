package com.mont.decor.service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mont.decor.dto.UsuarioDTO;
import com.mont.decor.model.Perfil;
import com.mont.decor.model.Usuario;
import com.mont.decor.repository.PerfilRepository;
import com.mont.decor.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	
    private final PerfilRepository perfilRepository;
    
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario criarUsuario(UsuarioDTO dto) {
    	usuarioRepository.findByNome(dto.nome())
        .ifPresent(
            usuario -> { 
                throw new RuntimeException("Já há um usuário cadastrado com esse nome");
            }
        );
    	
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setTelefone(dto.telefone());
        
        Set<Perfil> perfis = dto.perfisIds().stream()
            .map(perfilRepository::findById)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet());
        
        usuario.setPerfis(perfis);
        return usuarioRepository.save(usuario);
    }
    
    @Override
    public Usuario getUsuario(String usuario) {
    	return usuarioRepository.findByNome(usuario).get();
    }
}
