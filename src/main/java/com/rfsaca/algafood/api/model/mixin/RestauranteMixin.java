package com.rfsaca.algafood.api.model.mixin;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rfsaca.algafood.domain.models.Cozinha;
import com.rfsaca.algafood.domain.models.Endereco;
import com.rfsaca.algafood.domain.models.FormaPagamento;
import com.rfsaca.algafood.domain.models.Produto;

public abstract class RestauranteMixin {

    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private Cozinha cozinha;

    @JsonIgnore
    private Endereco endereco;

    @JsonIgnore
    private OffsetDateTime dataCadastro;

    @JsonIgnore
    private OffsetDateTime dataAtualizacao;

    @JsonIgnore
    private List<FormaPagamento> formasPagamento;

    @JsonIgnore
    private List<Produto> produtos;
}
