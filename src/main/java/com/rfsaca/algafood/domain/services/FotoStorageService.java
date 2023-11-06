package com.rfsaca.algafood.domain.services;

import com.lowagie.text.pdf.codec.Base64.InputStream;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);

    @Builder
    @Getter
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream;
    }
}
