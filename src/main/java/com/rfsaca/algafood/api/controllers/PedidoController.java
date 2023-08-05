package com.rfsaca.algafood.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rfsaca.algafood.api.assembler.PedidoDtoAssembler;
import com.rfsaca.algafood.api.assembler.PedidoResumoDtoAssembler;
import com.rfsaca.algafood.api.model.PedidoDto;
import com.rfsaca.algafood.api.model.PedidoResumoDto;
import com.rfsaca.algafood.domain.models.Pedido;
import com.rfsaca.algafood.domain.repositories.PedidoRepository;
import com.rfsaca.algafood.domain.services.EmissaoPedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private EmissaoPedidoService emissaoPedidoService;

    @Autowired
    private PedidoDtoAssembler pedidoDtoAssembler;

    @Autowired
    private PedidoResumoDtoAssembler pedidoResumoDtoAssembler;

    @GetMapping
    public List<PedidoResumoDto> listar() {
        List<Pedido> pedidos = pedidoRepository.findAll();

        return pedidoResumoDtoAssembler.toCollectionDto(pedidos);
    }

    @GetMapping("/{pedidoId}")
    public PedidoDto buscar(@PathVariable Long pedidoId) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);

        return pedidoDtoAssembler.toDto(pedido);
    }
}
