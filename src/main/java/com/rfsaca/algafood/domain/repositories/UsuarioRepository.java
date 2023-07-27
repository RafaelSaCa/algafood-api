package com.rfsaca.algafood.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rfsaca.algafood.domain.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
