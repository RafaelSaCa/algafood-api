package com.rfsaca.algafood.api.v1.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.rfsaca.algafood.api.v1.assembler.PedidoDtoAssembler;
import com.rfsaca.algafood.api.v1.assembler.PedidoInputDisassembler;
import com.rfsaca.algafood.api.v1.assembler.PedidoResumoDtoAssembler;
import com.rfsaca.algafood.api.v1.model.PedidoDto;
import com.rfsaca.algafood.api.v1.model.PedidoResumoDto;
import com.rfsaca.algafood.api.v1.model.input.PedidoInput;
import com.rfsaca.algafood.core.data.PageWrapper;
import com.rfsaca.algafood.core.data.PageableTranslator;
import com.rfsaca.algafood.domain.exceptions.EntidadeNaoEncontradaException;
import com.rfsaca.algafood.domain.exceptions.NegocioException;
import com.rfsaca.algafood.domain.filter.PedidoFilter;
import com.rfsaca.algafood.domain.models.Pedido;
import com.rfsaca.algafood.domain.models.Usuario;
import com.rfsaca.algafood.domain.repositories.PedidoRepository;
import com.rfsaca.algafood.domain.services.EmissaoPedidoService;
import com.rfsaca.algafood.infrastructure.repository.PedidoSpecs;

@RestController
@RequestMapping("/v1/pedidos")
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

    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

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
    public PagedModel<PedidoResumoDto> pesquisar(PedidoFilter filtro,
            @PageableDefault(size = 10) Pageable pageable) {
        pageable = traduzirPageable(pageable);

        Page<Pedido> pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);

        pedidosPage = new PageWrapper<>(pedidosPage, pageable);

        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoDtoAssembler);
    }

    @GetMapping("/{codigoPedido}")
    public PedidoDto buscar(@PathVariable String codigoPedido) {
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);

        return pedidoDtoAssembler.toModel(pedido);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDto adicionar(@Valid @RequestBody PedidoInput pedidoInput) {
        try {
            Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

            novoPedido.setCliente(new Usuario());// pegar usuario autenticado
            novoPedido.getCliente().setId(1L);

            novoPedido = emissaoPedidoService.emitir(novoPedido);

            return pedidoDtoAssembler.toModel(novoPedido);

        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getLocalizedMessage(), e);
        }
    }

    private Pageable traduzirPageable(Pageable apiPageable) {
        var mapeamento = ImmutableMap.of( // de - para
                "codigo", "codigo",
                "subtotal", "subtotal",
                "restaurante.nome", "restaurante.nome",
                "cliente.nome", "cliente.nome",
                "valorTotal", "valorTotal"

        );
        return PageableTranslator.translate(apiPageable, mapeamento);
    }
}
