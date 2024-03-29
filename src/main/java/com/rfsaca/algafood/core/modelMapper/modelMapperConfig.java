package com.rfsaca.algafood.core.modelMapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rfsaca.algafood.api.v1.model.EnderecoDto;
import com.rfsaca.algafood.api.v1.model.input.ItemPedidoInput;
import com.rfsaca.algafood.api.v2.model.input.CidadeInputV2;
import com.rfsaca.algafood.domain.models.Cidade;
import com.rfsaca.algafood.domain.models.Endereco;
import com.rfsaca.algafood.domain.models.ItemPedido;

@Configuration
public class modelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        // modelMapper.createTypeMap(Restaurante.class, RestauranteDto.class)
        // .addMapping(Restaurante::getTaxaFrete, RestauranteDto::setPrecoFrete);

        modelMapper.createTypeMap(CidadeInputV2.class, Cidade.class)
                .addMappings(mapper -> mapper.skip(Cidade::setId));

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));// mapper para NAO setar o id do item no pedido
                                                                       // ao fazer a conversao de dto para modelo de
                                                                       // dominio.

        var enderecoDtoTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDto.class);

        enderecoDtoTypeMap.<String>addMapping(enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecoDtoDestino, value) -> enderecoDtoDestino.getCidade().setEstado(value));

        return modelMapper;
    }
}
