package com.rfsaca.algafood.api.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;
import com.rfsaca.algafood.api.model.view.RestauranteView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDto {

    @JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
    private Long id;

    @JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
    private String nome;

    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal precoFrete;

    @JsonView(RestauranteView.Resumo.class)
    private CozinhaDto cozinha;

    private Boolean ativo;
    private EnderecoDto endereco;
    private Boolean aberto;

}