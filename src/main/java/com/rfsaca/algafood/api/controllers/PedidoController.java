package com.rfsaca.algafood.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.rfsaca.algafood.api.assembler.PedidoDtoAssembler;
import com.rfsaca.algafood.api.assembler.PedidoInputDisassembler;
import com.rfsaca.algafood.api.assembler.PedidoResumoDtoAssembler;
import com.rfsaca.algafood.api.model.PedidoDto;
import com.rfsaca.algafood.api.model.PedidoResumoDto;
import com.rfsaca.algafood.api.model.input.PedidoInput;
import com.rfsaca.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.rfsaca.algafood.domain.exceptions.NegocioException;
import com.rfsaca.algafood.domain.models.Pedido;
import com.rfsaca.algafood.domain.models.Usuario;
import com.rfsaca.algafood.domain.repositories.PedidoRepository;
import com.rfsaca.algafood.domain.repositories.filter.PedidoFilter;
import com.rfsaca.algafood.domain.services.EmissaoPedidoService;
import com.rfsaca.algafood.infrastructure.repository.PedidoSpecs;

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
    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    /*
     * / @GetMapping
     * public MappingJacksonValue listar(@RequestParam(required = false) String
     * campos) {
     * List<Pedido> pedidos = pedidoRepository.findAll();
     * List<PedidoResumoDto> pedidosDto =
     * pedidoResumoDtoAssembler.toCollectionDto(pedidos);
     * 
     * MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosDto);
     * 
     * SimpleFilterProvider filterProvider = new SimpleFilterProvider();
     * filterProvider.addFilter("pedidoFilter",
     * SimpleBeanPropertyFilter.serializeAll());
     * 
     * if (StringUtils.isNotBlank(campos)) {// filtros da requisicao
     * filterProvider.addFilter("pedidoFilter",
     * SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
     * }
     * 
     * pedidosWrapper.setFilters(filterProvider);
     * 
     * return pedidosWrapper;
     * 
     * }
     */

    @GetMapping
    public List<PedidoResumoDto> pesquisar(PedidoFilter filtro) {
        List<Pedido> todosPedidos = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro));

        return pedidoResumoDtoAssembler.toCollectionDto(todosPedidos);
    }

    @GetMapping("/{codigoPedido}")
    public PedidoDto buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);

        return pedidoDtoAssembler.toDto(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDto adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            novoPedido.setCliente(new Usuario());// pegar usuario autenticado
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedidoService.emitir(novoPedido);

            return pedidoDtoAssembler.toDto(novoPedido);

        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getLocalizedMessage(), e);
        }
    }
}
