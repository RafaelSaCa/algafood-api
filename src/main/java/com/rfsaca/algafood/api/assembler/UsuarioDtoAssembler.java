package com.rfsaca.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.model.UsuarioDto;
import com.rfsaca.algafood.domain.models.Usuario;

@Component
public class UsuarioDtoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDto toDto(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDto.class);
    }

    public List<UsuarioDto> toCollectionDto(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> toDto(usuario))
                .collect(Collectors.toList());

    }
}