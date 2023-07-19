package com.rfsaca.algafood.core.modelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rfsaca.algafood.api.model.RestauranteDto;
import com.rfsaca.algafood.domain.models.Restaurante;

@Configuration
public class modelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Restaurante.class, RestauranteDto.class)
                .addMapping(Restaurante::getTaxaFrete, RestauranteDto::setPrecoFrete);
        return modelMapper;
    }
}
