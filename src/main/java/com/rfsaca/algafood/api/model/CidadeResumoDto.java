package com.rfsaca.algafood.api.model;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Getter;
import lombok.Setter;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
public class CidadeResumoDto extends RepresentationModel<CidadeResumoDto> {

    private Long id;
    private String nome;
    private String estado;

}
