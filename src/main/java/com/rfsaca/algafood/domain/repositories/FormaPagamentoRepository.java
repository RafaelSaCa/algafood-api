package com.rfsaca.algafood.domain.repositories;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rfsaca.algafood.domain.models.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

    @Query("select max(dataAtualizacao) from FormaPagamento")
    OffsetDateTime getDataUltimaAtualizacao();

}
