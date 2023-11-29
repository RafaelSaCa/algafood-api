package com.rfsaca.algafood.api.v1.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Setter
@Getter
public class CidadeDto extends RepresentationModel<CidadeDto> {

    private Long id;
    private String nome;
    private EstadoDto estado;

}
