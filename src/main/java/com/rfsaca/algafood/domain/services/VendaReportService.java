package com.rfsaca.algafood.domain.services;

import com.rfsaca.algafood.domain.filter.VendaDiariaFilter;

import net.sf.jasperreports.engine.JRException;

public interface VendaReportService {

    byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) throws JRException;

}
