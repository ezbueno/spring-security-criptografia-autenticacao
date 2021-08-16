package com.buenoezandro.encriptar.senha.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.buenoezandro.encriptar.senha.model.dto.UsuarioDTO;
import com.buenoezandro.encriptar.senha.service.UsuarioService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioController {

	private final UsuarioService usuarioService ;

	@GetMapping(value = "/listarTodos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<UsuarioDTO>> listarTodos() {
		List<UsuarioDTO> usuarios = this.usuarioService.listarTodos();
		return ResponseEntity.ok(usuarios);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioDTO> buscar(@PathVariable Integer id) {
		var usuarioEncontrado = this.usuarioService.buscarPorId(id);
		return ResponseEntity.ok(usuarioEncontrado);
	}

	@GetMapping(value = "/validarSenha", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> validarSenha(@RequestParam String login, @RequestParam String password) {
		var senhaValida = this.usuarioService.validarSenha(login, password);
		return ResponseEntity.ok(senhaValida);
	}

	@PostMapping(value = "/salvar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UsuarioDTO> salvar(@RequestBody UsuarioDTO usuarioDTO) {
		var novoUsuario = this.usuarioService.salvar(usuarioDTO);
		var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(novoUsuario.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
}
