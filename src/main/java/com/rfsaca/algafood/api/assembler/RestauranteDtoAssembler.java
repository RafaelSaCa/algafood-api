package com.rfsaca.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.AlgaLinks;
import com.rfsaca.algafood.api.controllers.RestauranteController;
import com.rfsaca.algafood.api.model.RestauranteDto;
import com.rfsaca.algafood.domain.models.Restaurante;

@Component
public class RestauranteDtoAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDto> {

    @Autowired
    private ModelMapper modelMapper;

    private AlgaLinks algaLinks;

    public RestauranteDtoAssembler(Class<?> controllerClass, Class<RestauranteDto> resourceType) {
        super(RestauranteController.class, RestauranteDto.class);

    }

    @Override
    public RestauranteDto toModel(Restaurante restaurante) {
        RestauranteDto restauranteDto = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteDto);

        restauranteDto.add(algaLinks.linkToRestaurantes("restaurantes"));

        restauranteDto.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));

        restauranteDto.getEndereco().getCidade()
                .add(algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));

        restauranteDto.add(algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));

        restauranteDto.add(algaLinks.linkToResponsaveisRestaurante(restaurante.getId(), "responsaveis"));

        return restauranteDto;
    }

    @Override
    public CollectionModel<RestauranteDto> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToRestaurantes());
    }

}
