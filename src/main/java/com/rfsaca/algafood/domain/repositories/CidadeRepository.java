package com.rfsaca.algafood.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rfsaca.algafood.domain.models.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}
