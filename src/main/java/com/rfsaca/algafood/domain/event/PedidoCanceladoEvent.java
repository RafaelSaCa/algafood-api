package com.rfsaca.algafood.domain.event;

import com.rfsaca.algafood.domain.models.Pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoCanceladoEvent {

    private Pedido pedido;

}
