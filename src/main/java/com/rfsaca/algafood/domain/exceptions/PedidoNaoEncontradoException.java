package com.rfsaca.algafood.domain.exceptions;

public class PedidoNaoEncontradoException extends NegocioException {

    public PedidoNaoEncontradoException(String codigoPedido) {
        super(String.format("Não existe um pedido com código %s", codigoPedido));
    }

}
