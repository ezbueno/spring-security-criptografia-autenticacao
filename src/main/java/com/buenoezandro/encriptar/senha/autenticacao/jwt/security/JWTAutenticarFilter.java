package com.buenoezandro.encriptar.senha.autenticacao.jwt.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.buenoezandro.encriptar.senha.autenticacao.jwt.DetalheUsuarioData;
import com.buenoezandro.encriptar.senha.autenticacao.jwt.exception.UsuarioNaoAutenticadoException;
import com.buenoezandro.encriptar.senha.model.Usuario;
import com.buenoezandro.encriptar.senha.utils.MessageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JWTAutenticarFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	public static final int TOKEN_EXPIRACAO = 600_000;
	public static final String TOKEN_SENHA = "02991c60-0ca6-42de-90ef-1734d1e91940";

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {
			var usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);

			return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getLogin(),
					usuario.getPassword(), new ArrayList<>()));

		} catch (IOException e) {
			throw new UsuarioNaoAutenticadoException(MessageUtils.USUARIO_NAO_AUTORIZADO);
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		var usuarioData = (DetalheUsuarioData) authResult.getPrincipal();

		String token = JWT.create().withSubject(usuarioData.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO))
				.sign(Algorithm.HMAC512(TOKEN_SENHA));
		
		response.getWriter().write(token);
		response.getWriter().flush();

	}

}