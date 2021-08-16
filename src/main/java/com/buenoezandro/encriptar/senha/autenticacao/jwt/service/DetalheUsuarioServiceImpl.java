package com.buenoezandro.encriptar.senha.autenticacao.jwt.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.buenoezandro.encriptar.senha.autenticacao.jwt.DetalheUsuarioData;
import com.buenoezandro.encriptar.senha.repository.UsuarioRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class DetalheUsuarioServiceImpl implements UserDetailsService {

	private final UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var usuario = this.usuarioRepository.findByLogin(username);

		if (usuario.isEmpty()) {
			throw new UsernameNotFoundException("Usuário [" + username + "] não encontrado!");
		}

		return new DetalheUsuarioData(usuario);
	}

}