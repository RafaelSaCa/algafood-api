package com.rfsaca.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.AlgaLinks;
import com.rfsaca.algafood.api.controllers.EstadoController;
import com.rfsaca.algafood.api.model.EstadoDto;
import com.rfsaca.algafood.domain.models.Estado;

@Component
public class EstadoDtoAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDto> {

    public EstadoDtoAssembler() {
        super(EstadoController.class, EstadoDto.class);

    }

    @Autowired
    private ModelMapper modelMapper;

    private AlgaLinks algaLinks;

    public EstadoDto toDto(Estado estado) {
        EstadoDto estadoDto = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, EstadoDto.class);

        estadoDto.add(algaLinks.linkToEstados("estados"));

        return estadoDto;
    }

    public List<EstadoDto> toCollectionDto(List<Estado> estados) {
        return estados.stream()
                .map(estado -> toDto(estado))
                .collect(Collectors.toList());
    }

    @Override
    public EstadoDto toModel(Estado estado) {
        EstadoDto estadoModel = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, estadoModel);

        estadoModel.add(linkTo(EstadoController.class).withRel("estados"));

        return estadoModel;
    }

    @Override
    public CollectionModel<EstadoDto> toCollectionModel(Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(EstadoController.class).withSelfRel());
    }
}