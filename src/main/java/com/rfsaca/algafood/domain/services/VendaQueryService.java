package com.rfsaca.algafood.domain.services;

import java.util.List;

import com.rfsaca.algafood.domain.filter.VendaDiariaFilter;
import com.rfsaca.algafood.domain.models.dto.VendaDiaria;

public interface VendaQueryService {

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
