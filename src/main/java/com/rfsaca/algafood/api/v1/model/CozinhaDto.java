package com.rfsaca.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cozinhas")
@Setter
@Getter
public class CozinhaDto extends RepresentationModel<CozinhaDto> {

    // @JsonView(RestauranteView.Resumo.class)
    private Long id;

    // @JsonView(RestauranteView.Resumo.class)
    private String nome;
}
