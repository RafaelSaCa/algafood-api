package com.rfsaca.algafood.api.v2.model;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeDtoV2 extends RepresentationModel<CidadeDtoV2> {

    private Long idCidade;
    private String nomeCidade;
    private Long idEstado;
    private String nomeEstado;

}
