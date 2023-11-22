package com.rfsaca.algafood.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.AlgaLinks;
import com.rfsaca.algafood.api.controllers.PedidoController;
import com.rfsaca.algafood.api.controllers.RestauranteController;
import com.rfsaca.algafood.api.controllers.UsuarioController;
import com.rfsaca.algafood.api.model.PedidoResumoDto;
import com.rfsaca.algafood.domain.models.Pedido;

@Component
public class PedidoResumoDtoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoDto> {

    public PedidoResumoDtoAssembler() {
        super(PedidoController.class, PedidoResumoDto.class);

    }

    @Autowired
    private ModelMapper modelMapper;

    private AlgaLinks algaLinks;

    @Override
    public PedidoResumoDto toModel(Pedido pedido) {
        PedidoResumoDto pedidoDto = createModelWithId(pedido.getCodigo(), pedido);
        modelMapper.map(pedido, pedidoDto);

        pedidoDto.add(algaLinks.linkToPedidos("pedidos"));

        pedidoDto.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                .buscar(pedido.getRestaurante().getId())).withSelfRel());

        pedidoDto.getCliente().add(linkTo(methodOn(UsuarioController.class)
                .buscar(pedido.getCliente().getId())).withSelfRel());

        return pedidoDto;
    }
    /*
     * public List<PedidoResumoDto> toCollectionDto(Collection<Pedido> pedidos) {
     * return pedidos.stream()
     * .map(pedido -> toModel(pedido))
     * .collect(Collectors.toList());
     * }
     */
}
