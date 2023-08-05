package com.rfsaca.algafood.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rfsaca.algafood.domain.exceptions.PedidoNaoEncontradoException;
import com.rfsaca.algafood.domain.models.Pedido;
import com.rfsaca.algafood.domain.repositories.PedidoRepository;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido buscarOuFalhar(Long pedidoId) {
        return pedidoRepository.findById(pedidoId).orElseThrow(() -> new PedidoNaoEncontradoException(pedidoId));
    }
}
