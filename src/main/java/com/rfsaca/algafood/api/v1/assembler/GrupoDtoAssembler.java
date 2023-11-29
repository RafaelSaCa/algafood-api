package com.rfsaca.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.v1.AlgaLinks;
import com.rfsaca.algafood.api.v1.controllers.GrupoController;
import com.rfsaca.algafood.api.v1.model.GrupoDto;
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
        grupoDto.add(algaLinks.linkToGruposPermissoes(grupo.getId(), "permissoes"));

        return grupoDto;
    }

    @Override
    public CollectionModel<GrupoDto> toCollectionModel(Iterable<? extends Grupo> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToGrupos("grupos"));
    }
}
