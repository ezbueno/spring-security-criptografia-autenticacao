package com.buenoezandro.encriptar.senha.service.exception;

public class UsuarioNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsuarioNaoEncontradoException(Integer id) {
		super(String.format("Usuário com ID: %s não encontrado!", id));
	}

}