package com.rfsaca.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rfsaca.algafood.api.model.mixin.CidadeMixin;
import com.rfsaca.algafood.api.model.mixin.CozinhaMixin;
import com.rfsaca.algafood.domain.models.Cidade;
import com.rfsaca.algafood.domain.models.Cozinha;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Cidade.class, CidadeMixin.class);
        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);

    }

}
