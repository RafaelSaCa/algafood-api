package com.rfsaca.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.controllers.CidadeController;
import com.rfsaca.algafood.api.controllers.EstadoController;
import com.rfsaca.algafood.api.model.CidadeDto;
import com.rfsaca.algafood.domain.models.Cidade;

@Component
public class CidadeDtoAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDto> {

    public CidadeDtoAssembler() {
        super(CidadeController.class, CidadeDto.class);

    }

    @Autowired
    private ModelMapper modelMapper;

    public List<CidadeDto> toCollectionDto(List<Cidade> cidades) {
        return cidades.stream()
                .map(cidade -> toModel(cidade))
                .collect(Collectors.toList());
    }

    @Override
    public CidadeDto toModel(Cidade cidade) {
        CidadeDto cidadeDto = createModelWithId(cidade.getId(), cidade);

        modelMapper.map(cidade, cidadeDto);

        cidadeDto.add(WebMvcLinkBuilder.linkTo(CidadeController.class).withRel("cidades"));

        cidadeDto.getEstado().add(
                WebMvcLinkBuilder.linkTo(EstadoController.class).slash(cidadeDto.getEstado().getId())
                        .withSelfRel());

        return cidadeDto;
    }

    @Override
    public CollectionModel<CidadeDto> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(CidadeController.class).withSelfRel());
    }

}
