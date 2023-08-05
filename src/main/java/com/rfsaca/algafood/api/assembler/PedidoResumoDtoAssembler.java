package com.rfsaca.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.model.PedidoResumoDto;
import com.rfsaca.algafood.domain.models.Pedido;

@Component
public class PedidoResumoDtoAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResumoDto toDto(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResumoDto.class);
    }

    public List<PedidoResumoDto> toCollectionDto(Collection<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toDto(pedido))
                .collect(Collectors.toList());
    }
}
