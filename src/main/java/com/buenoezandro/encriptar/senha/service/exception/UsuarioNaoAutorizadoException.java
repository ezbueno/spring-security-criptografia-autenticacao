package com.buenoezandro.encriptar.senha.service.exception;

public class UsuarioNaoAutorizadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsuarioNaoAutorizadoException(String message) {
		super(message);
	}

}