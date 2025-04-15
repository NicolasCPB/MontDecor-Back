package com.mont.decor.service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.mont.decor.dto.UsuarioDTO;
import com.mont.decor.exceptions.UsuarioJaExisteException;
import com.mont.decor.model.Perfil;
import com.mont.decor.model.Usuario;
import com.mont.decor.repository.PerfilRepository;
import com.mont.decor.repository.UsuarioRepository;
import com.mont.decor.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	
    private final PerfilRepository perfilRepository;
    
    private final PasswordEncoder passwordEncoder;
    
    private final AuthenticationManager authenticationManager;
    
    private final JwtUtil jwtUtil;

    @Override
    public String login(String usuario, String senha) {
    	Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                usuario,
                senha
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        Usuario usuarioDetalhes = getUsuario(usuario);

        return jwtUtil.generateToken(usuario, authentication.getAuthorities(), usuarioDetalhes.getTelefone());
    }
    
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
    
    @Override
    public String editarUsuario(UsuarioDTO usuarioDTO) {
    	if (!usuarioDTO.nome().equals(usuarioDTO.nomeAntigo())) {
    		try {
    			getUsuario(usuarioDTO.nome());
    			throw new UsuarioJaExisteException("Já existe um usuário com esse nome.");
    		} catch (NoSuchElementException exception) {
    		}
    	}
    	Usuario usuarioModel = getUsuario(usuarioDTO.nomeAntigo());
    	usuarioModel.setNome(usuarioDTO.nome());
    	usuarioModel.setTelefone(usuarioDTO.telefone());
    	if (!ObjectUtils.isEmpty(usuarioDTO.senha())) {
    		usuarioModel.setSenha(passwordEncoder.encode(usuarioDTO.senha()));
    	}
    	
    	return login(usuarioModel.getNome(), usuarioModel.getSenha());
    }
}
