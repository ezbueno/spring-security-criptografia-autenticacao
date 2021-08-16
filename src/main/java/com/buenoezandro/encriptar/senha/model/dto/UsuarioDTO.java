package com.buenoezandro.encriptar.senha.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotBlank
	@Size(max = 30)
	private String login;

	@NotBlank
	@Size(max = 30)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;

}