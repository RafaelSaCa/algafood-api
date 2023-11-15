package com.rfsaca.algafood.api.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
public class EstadoDto extends RepresentationModel<EstadoDto> {

    private Long id;
    private String nome;
}
