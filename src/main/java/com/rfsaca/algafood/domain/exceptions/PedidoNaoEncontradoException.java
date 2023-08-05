package com.rfsaca.algafood.domain.exceptions;

public class PedidoNaoEncontradoException extends NegocioException {

    public PedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
        // TODO Auto-generated constructor stub
    }

    public PedidoNaoEncontradoException(Long pedidoId) {
        this(String.format("Não existe um pedido com código %d", pedidoId));
    }

}
