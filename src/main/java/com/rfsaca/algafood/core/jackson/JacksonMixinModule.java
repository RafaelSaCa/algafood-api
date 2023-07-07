package com.rfsaca.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rfsaca.algafood.api.model.mixin.RestauranteMixin;
import com.rfsaca.algafood.domain.models.Restaurante;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
    }

}
