package com.buenoezandro.encriptar.senha.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buenoezandro.encriptar.senha.encoder.PasswordEncoder;
import com.buenoezandro.encriptar.senha.mapper.UsuarioMapper;
import com.buenoezandro.encriptar.senha.model.dto.UsuarioDTO;
import com.buenoezandro.encriptar.senha.repository.UsuarioRepository;
import com.buenoezandro.encriptar.senha.service.exception.UsuarioNaoAutorizadoException;
import com.buenoezandro.encriptar.senha.service.exception.UsuarioNaoEncontradoException;
import com.buenoezandro.encriptar.senha.utils.MessageUtils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder encoder;
	private static final UsuarioMapper USUARIO_MAPPER = UsuarioMapper.INSTANCE;

	@Transactional(readOnly = true)
	@Override
	public List<UsuarioDTO> listarTodos() {
		return this.usuarioRepository.findAll().stream().map(USUARIO_MAPPER::toDTO).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	@Override
	public UsuarioDTO buscarPorId(Integer id) {
		var usuarioEncontrado = this.usuarioRepository.findById(id)
				.orElseThrow(() -> new UsuarioNaoEncontradoException(id));
		return USUARIO_MAPPER.toDTO(usuarioEncontrado);
	}

	@Transactional(readOnly = true)
	@Override
	public boolean validarSenha(String login, String password) {
		var optUsuario = this.usuarioRepository.findByLogin(login);

		if (optUsuario.isEmpty()) {
			throw new UsuarioNaoAutorizadoException(MessageUtils.USUARIO_NAO_AUTORIZADO);
		}

		var senhaValida = this.encoder.getPasswordEncoder().matches(password, optUsuario.get().getPassword());

		if (!senhaValida) {
			throw new UsuarioNaoAutorizadoException(MessageUtils.USUARIO_NAO_AUTORIZADO);
		}

		return true;
	}

	@Transactional
	@Override
	public UsuarioDTO salvar(UsuarioDTO usuarioDTO) {
		usuarioDTO.setId(null);
		this.criptografarSenha(usuarioDTO);

		var novoUsuario = this.usuarioRepository.save(USUARIO_MAPPER.toModel(usuarioDTO));
		return USUARIO_MAPPER.toDTO(novoUsuario);
	}

	private void criptografarSenha(UsuarioDTO usuarioDTO) {
		usuarioDTO.setPassword(this.encoder.getPasswordEncoder().encode(usuarioDTO.getPassword()));
	}

}