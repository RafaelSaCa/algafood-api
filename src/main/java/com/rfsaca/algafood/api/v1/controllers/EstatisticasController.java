package com.rfsaca.algafood.api.v1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.rfsaca.algafood.domain.filter.VendaDiariaFilter;
import com.rfsaca.algafood.domain.models.dto.VendaDiaria;
import com.rfsaca.algafood.domain.services.VendaQueryService;
import com.rfsaca.algafood.domain.services.VendaReportService;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping(path = "/v1/estatisticas")
public class EstatisticasController {
    @Autowired
    private VendaQueryService vendaQueryService;

    @Autowired
    private VendaReportService vendaReportService;

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
            @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
    }

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro,
            @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) throws JRException {

        byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffset);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);
    }

}
