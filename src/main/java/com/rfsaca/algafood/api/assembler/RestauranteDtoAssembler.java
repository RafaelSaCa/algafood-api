package com.rfsaca.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.model.CozinhaDto;
import com.rfsaca.algafood.api.model.RestauranteDto;
import com.rfsaca.algafood.domain.models.Restaurante;

@Component
public class RestauranteDtoAssembler {

    public RestauranteDto toDto(Restaurante restaurante) {

        CozinhaDto cozinhaDto = new CozinhaDto();
        cozinhaDto.setId(restaurante.getCozinha().getId());
        cozinhaDto.setNome(restaurante.getCozinha().getNome());

        RestauranteDto restauranteDto = new RestauranteDto();
        restauranteDto.setId(restaurante.getId());
        restauranteDto.setNome(restaurante.getNome());
        restauranteDto.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteDto.setCozinha(cozinhaDto);
        return restauranteDto;
    }

    public List<RestauranteDto> toCollectionDto(List<Restaurante> restaurantes) {
        return restaurantes.stream()
                .map(restaurante -> toDto(restaurante))
                .collect(Collectors.toList());

    }
}
