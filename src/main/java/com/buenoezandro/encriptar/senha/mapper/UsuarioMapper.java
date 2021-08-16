package com.buenoezandro.encriptar.senha.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.buenoezandro.encriptar.senha.model.Usuario;
import com.buenoezandro.encriptar.senha.model.dto.UsuarioDTO;

@Mapper
public interface UsuarioMapper {

	UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

	Usuario toModel(UsuarioDTO usuarioDTO);

	UsuarioDTO toDTO(Usuario usuario);
		
}