package com.rfsaca.algafood.api.v1.model;

import java.math.BigDecimal;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteDto extends RepresentationModel<RestauranteDto> {

    // @JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
    private Long id;

    // @JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
    private String nome;

    // @JsonView(RestauranteView.Resumo.class)
    private BigDecimal precoFrete;

    // @JsonView(RestauranteView.Resumo.class)
    private CozinhaDto cozinha;

    private Boolean ativo;
    private EnderecoDto endereco;
    private Boolean aberto;

}