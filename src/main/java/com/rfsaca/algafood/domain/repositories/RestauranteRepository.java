package com.rfsaca.algafood.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rfsaca.algafood.domain.models.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

}
