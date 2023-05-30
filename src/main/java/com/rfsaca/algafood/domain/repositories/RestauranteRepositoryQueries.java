package com.rfsaca.algafood.domain.repositories;

import java.math.BigDecimal;
import java.util.List;

import com.rfsaca.algafood.domain.models.Restaurante;

public interface RestauranteRepositoryQueries {

    List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

    List<Restaurante> findComFreteGratis(String nome);

}
