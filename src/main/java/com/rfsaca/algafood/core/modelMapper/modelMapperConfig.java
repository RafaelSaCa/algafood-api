package com.rfsaca.algafood.core.modelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rfsaca.algafood.api.model.EnderecoDto;
import com.rfsaca.algafood.domain.models.Endereco;

@Configuration
public class modelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        // modelMapper.createTypeMap(Restaurante.class, RestauranteDto.class)
        // .addMapping(Restaurante::getTaxaFrete, RestauranteDto::setPrecoFrete);

        var enderecoDtoTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDto.class);

        enderecoDtoTypeMap.<String>addMapping(enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoDtoDestino, value) -> enderecoDtoDestino.getCidade().setEstado(value));

        return modelMapper;
    }
}
