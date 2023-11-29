package com.rfsaca.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.v1.AlgaLinks;
import com.rfsaca.algafood.api.v1.controllers.RestauranteController;
import com.rfsaca.algafood.api.v1.model.RestauranteDto;
import com.rfsaca.algafood.domain.models.Restaurante;

@Component
public class RestauranteDtoAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDto> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public RestauranteDtoAssembler() {
        super(RestauranteController.class, RestauranteDto.class);

    }

    @Override
    public RestauranteDto toModel(Restaurante restaurante) {
        RestauranteDto restauranteDto = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteDto);

        restauranteDto.add(algaLinks.linkToRestaurantes("restaurantes"));

        if (restaurante.ativacaoPermitida()) {
            restauranteDto.add(
                    algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
        }

        if (restaurante.inativacaoPermitida()) {
            restauranteDto.add(
                    algaLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
        }

        if (restaurante.aberturaPermitida()) {
            restauranteDto.add(
                    algaLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
        }

        if (restaurante.fechamentoPermitido()) {
            restauranteDto.add(
                    algaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
        }

        restauranteDto.add(algaLinks.linkToRestaurantes("restaurantes"));

        restauranteDto.add(algaLinks.linkToProdutos(restaurante.getId(), "produtos"));

        restauranteDto.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));

        if (restauranteDto.getEndereco() != null && restauranteDto.getEndereco().getCidade() != null) {
            restauranteDto.getEndereco().getCidade()
                    .add(algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
        }

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
