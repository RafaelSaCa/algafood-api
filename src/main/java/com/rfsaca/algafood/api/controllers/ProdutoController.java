package com.rfsaca.algafood.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rfsaca.algafood.api.AlgaLinks;
import com.rfsaca.algafood.api.assembler.ProdutoDtoAssembler;
import com.rfsaca.algafood.api.assembler.ProdutoDtoInputDisassembler;
import com.rfsaca.algafood.api.model.ProdutoDto;
import com.rfsaca.algafood.api.model.input.ProdutoInput;
import com.rfsaca.algafood.domain.models.Produto;
import com.rfsaca.algafood.domain.models.Restaurante;
import com.rfsaca.algafood.domain.repositories.ProdutoRepository;
import com.rfsaca.algafood.domain.services.ProdutoService;
import com.rfsaca.algafood.domain.services.RestauranteService;

import freemarker.ext.beans.CollectionModel;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class ProdutoController {

    @Autowired
    private RestauranteService restauranteService;
    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ProdutoDtoAssembler produtoDtoAssembler;
    @Autowired
    private ProdutoDtoInputDisassembler produtoDtoInputDisassembler;

    private AlgaLinks algaLinks;

    @GetMapping
    public org.springframework.hateoas.CollectionModel<ProdutoDto> listar(@PathVariable Long restauranteId,
            @RequestParam(required = false, defaultValue = "false") Boolean incluirInativos) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        List<Produto> todosProdutos = null;

        if (incluirInativos) {
            todosProdutos = produtoRepository.findTodosByRestaurante(restaurante);
        } else {
            todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);
        }

        return produtoDtoAssembler.toCollectionModel(todosProdutos).add(algaLinks.linkToProdutos(restauranteId));

    }

    @GetMapping("/{produtoId}")
    public ProdutoDto buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);

        return produtoDtoAssembler.toModel(produto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoDto adicionar(@PathVariable Long restauranteId, @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produto = produtoDtoInputDisassembler.toDomainObject(produtoInput);
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);
        produto.setRestaurante(restaurante);
        produto = produtoService.salvar(produto);

        return produtoDtoAssembler.toModel(produto);

    }

    @PutMapping("/{produtoId}")
    public ProdutoDto atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
            @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = produtoService.buscarOuFalhar(restauranteId, produtoId);

        produtoDtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);

        produtoAtual = produtoService.salvar(produtoAtual);

        return produtoDtoAssembler.toModel(produtoAtual);
    }

}
