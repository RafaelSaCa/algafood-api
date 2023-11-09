package com.rfsaca.algafood.domain.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rfsaca.algafood.domain.models.Produto;
import com.rfsaca.algafood.domain.models.Restaurante;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

        @Query("from Produto where restaurante.id = :restaurante and id = :produto")
        Optional<Produto> findById(@Param("restaurante") Long restauranteId,
                        @Param("produto") Long produtoId);

        List<Produto> findTodosByRestaurante(Restaurante restaurante);

        @Query("from Produto p where p.ativo = true and p.restaurante = :restaurante")
        List<Produto> findAtivosByRestaurante(Restaurante restaurante);

}
