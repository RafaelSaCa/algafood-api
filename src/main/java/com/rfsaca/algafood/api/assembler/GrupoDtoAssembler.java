package com.rfsaca.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.AlgaLinks;
import com.rfsaca.algafood.api.controllers.GrupoController;
import com.rfsaca.algafood.api.model.GrupoDto;
import com.rfsaca.algafood.domain.models.Grupo;

@Component
public class GrupoDtoAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoDto> {

    @Autowired
    private ModelMapper modelMapper;

    private AlgaLinks algaLinks;

    public GrupoDtoAssembler() {
        super(GrupoController.class, GrupoDto.class);
    }

    @Override
    public GrupoDto toModel(Grupo grupo) {
        GrupoDto grupoDto = createModelWithId(grupo.getId(), grupo);
        modelMapper.map(grupo, grupoDto);

        grupoDto.add(algaLinks.linkToGrupos("grupos"));
        grupoDto.add(algaLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));

        return grupoDto;
    }

    @Override
    public CollectionModel<GrupoDto> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToGrupos());
    }
}
