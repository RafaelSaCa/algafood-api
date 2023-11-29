package com.rfsaca.algafood.api.v1.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "restaurantes")
@Getter
@Setter
public class RestauranteResumoDto extends RepresentationModel<RestauranteResumoDto> {

    private Long id;
    private String nome;

}
