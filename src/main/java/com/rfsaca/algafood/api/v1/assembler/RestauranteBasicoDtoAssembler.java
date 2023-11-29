package com.rfsaca.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.osgi.service.component.annotations.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import com.rfsaca.algafood.api.v1.AlgaLinks;
import com.rfsaca.algafood.api.v1.controllers.RestauranteController;
import com.rfsaca.algafood.api.v1.model.RestauranteBasicoDto;
import com.rfsaca.algafood.domain.models.Restaurante;

@Component
public class RestauranteBasicoDtoAssembler
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoDto> {

    @Autowired
    private ModelMapper modelMapper;

    private AlgaLinks algaLinks;

    public RestauranteBasicoDtoAssembler() {
        super(RestauranteController.class, RestauranteBasicoDto.class);

    }

    @Override
    public RestauranteBasicoDto toModel(Restaurante restaurante) {
        RestauranteBasicoDto restauranteModel = createModelWithId(
                restaurante.getId(), restaurante);

        modelMapper.map(restaurante, restauranteModel);

        restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));

        restauranteModel.getCozinha().add(
                algaLinks.linkToCozinha(restaurante.getCozinha().getId()));

        return restauranteModel;
    }

    @Override
    public CollectionModel<RestauranteBasicoDto> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToRestaurantes());
    }
}