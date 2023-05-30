package com.rfsaca.algafood.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rfsaca.algafood.domain.models.Permissao;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {
}
