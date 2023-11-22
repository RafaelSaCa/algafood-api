package com.rfsaca.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import com.rfsaca.algafood.api.AlgaLinks;
import com.rfsaca.algafood.api.controllers.RestauranteController;
import com.rfsaca.algafood.api.model.RestauranteApenasNomeDto;
import com.rfsaca.algafood.domain.models.Restaurante;

public class RestauranteApenasNomeDtoAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public RestauranteApenasNomeDtoAssembler() {
        super(RestauranteController.class, RestauranteApenasNomeDto.class);
    }

    @Override
    public RestauranteApenasNomeDto toModel(Restaurante restaurante) {
        RestauranteApenasNomeDto restauranteDto = createModelWithId(
                restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteDto);

        restauranteDto.add(algaLinks.linkToRestaurantes("restaurantes"));

        return restauranteDto;
    }

    @Override
    public CollectionModel<RestauranteApenasNomeDto> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToRestaurantes());
    }
}
