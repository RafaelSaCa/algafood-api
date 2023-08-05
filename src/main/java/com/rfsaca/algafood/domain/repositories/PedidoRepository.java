package com.rfsaca.algafood.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rfsaca.algafood.domain.models.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
