package com.rfsaca.algafood.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rfsaca.algafood.api.AlgaLinks;
import com.rfsaca.algafood.api.assembler.FormaPagamentoDtoAssembler;
import com.rfsaca.algafood.api.model.FormaPagamentoDto;
import com.rfsaca.algafood.domain.models.Restaurante;
import com.rfsaca.algafood.domain.services.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private FormaPagamentoDtoAssembler formaPagamentoDtoAssembler;

    private AlgaLinks algaLinks;

    @GetMapping
    public CollectionModel<FormaPagamentoDto> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        CollectionModel<FormaPagamentoDto> formasPagamentoDto = formaPagamentoDtoAssembler
                .toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks()
                .add(algaLinks.linkToRestauranteFormasPagamento(restauranteId))
                .add(algaLinks.linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associar"));

        formasPagamentoDto.getContent().forEach(formaPagamentoDto -> {
            formaPagamentoDto.add(algaLinks.linkToRestauranteFormaPagamentoDesassociacao(restauranteId,
                    formaPagamentoDto.getId(), "desassociar"));
        });
        return formasPagamentoDto;
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);

        return ResponseEntity.noContent().build();
    }

}