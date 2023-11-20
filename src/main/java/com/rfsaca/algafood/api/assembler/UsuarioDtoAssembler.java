package com.rfsaca.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.AlgaLinks;
import com.rfsaca.algafood.api.controllers.UsuarioController;
import com.rfsaca.algafood.api.controllers.UsuarioGrupoController;
import com.rfsaca.algafood.api.model.UsuarioDto;
import com.rfsaca.algafood.domain.models.Usuario;

@Component
public class UsuarioDtoAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDto> {

    public UsuarioDtoAssembler() {
        super(UsuarioController.class, UsuarioDto.class);
    }

    @Autowired
    private ModelMapper modelMapper;

    private AlgaLinks algaLinks;

    public UsuarioDto toDto(Usuario usuario) {
        UsuarioDto usuarioDto = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, UsuarioDto.class);

        usuarioDto.add(algaLinks.linkToUsuarios("usuarios"));
        usuarioDto.add(algaLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));

        return usuarioDto;  
    }

    public List<UsuarioDto> toCollectionDto(Collection<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> toDto(usuario))
                .collect(Collectors.toList());

    }

    @Override
    public UsuarioDto toModel(Usuario usuario) {
        UsuarioDto usuarioDto = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, usuarioDto);

        usuarioDto.add(linkTo(UsuarioController.class).withRel("usuarios"));

        usuarioDto.add(linkTo(methodOn(UsuarioGrupoController.class)
                .listar(usuario.getId())).withRel("grupos-usuario"));

        return usuarioDto;
    }

    @Override
    public CollectionModel<UsuarioDto> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(UsuarioController.class).withSelfRel());
    }

}
