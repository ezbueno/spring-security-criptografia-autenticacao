package com.buenoezandro.encriptar.senha.service;

import java.util.List;

import com.buenoezandro.encriptar.senha.model.dto.UsuarioDTO;

public interface UsuarioService {
	
	List<UsuarioDTO> listarTodos();
	
	UsuarioDTO buscarPorId(Integer id);
	
	boolean validarSenha(String login, String password);

	UsuarioDTO salvar(UsuarioDTO usuarioDTO);

}