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
import com.rfsaca.algafood.api.controllers.CidadeController;
import com.rfsaca.algafood.api.controllers.FormaPagamentoController;
import com.rfsaca.algafood.api.controllers.PedidoController;
import com.rfsaca.algafood.api.controllers.ProdutoController;
import com.rfsaca.algafood.api.controllers.RestauranteController;
import com.rfsaca.algafood.api.controllers.UsuarioController;
import com.rfsaca.algafood.api.model.PedidoDto;
import com.rfsaca.algafood.domain.models.Pedido;

@Component
public class PedidoDtoAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoDto> {

        public PedidoDtoAssembler() {
                super(PedidoController.class, PedidoDto.class);

        }

        @Autowired
        private ModelMapper modelMapper;

        private AlgaLinks algaLinks;

        @Override
        public PedidoDto toModel(Pedido pedido) {

                PedidoDto pedidoDto = createModelWithId(pedido.getCodigo(), pedido);
                modelMapper.map(pedido, pedidoDto);

                pedidoDto.add(algaLinks.linkToPedidos("pedidos"));

                pedidoDto.getRestaurante().add(linkTo(methodOn(RestauranteController.class)
                                .buscar(pedido.getRestaurante().getId())).withSelfRel());

                pedidoDto.getCliente().add(linkTo(methodOn(UsuarioController.class)
                                .buscar(pedido.getCliente().getId())).withSelfRel());

                pedidoDto.getFormaPagamento().add(linkTo(methodOn(FormaPagamentoController.class)
                                .buscar(pedido.getFormaPagamento().getId(), null)).withSelfRel());

                pedidoDto.getEnderecoEntrega().getCidade().add(linkTo(methodOn(CidadeController.class)
                                .buscar(pedido.getEnderecoEntrega().getCidade().getId())).withSelfRel());

                pedidoDto.getItens().forEach(item -> {
                        item.add(linkTo(methodOn(ProdutoController.class)
                                        .buscar(pedidoDto.getRestaurante().getId(), item.getProdutoId()))
                                        .withRel("produto"));
                });

                return pedidoDto;
        }

        public List<PedidoDto> toCollectionDto(Collection<Pedido> pedidos) {
                return pedidos.stream()
                                .map(pedido -> toModel(pedido))
                                .collect(Collectors.toList());
        }
}
