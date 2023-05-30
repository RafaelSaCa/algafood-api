package com.rfsaca.algafood.domain.exceptions;

public class RestauranteNaoEncontradoException extends NegocioException {

    public RestauranteNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public RestauranteNaoEncontradoException(Long restauranteId) {
        this(String.format("Não existe um cadastro de restaurante com código %d", restauranteId));
    }

}
