package com.rfsaca.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rfsaca.algafood.api.model.input.RestauranteInput;
import com.rfsaca.algafood.domain.models.Cozinha;
import com.rfsaca.algafood.domain.models.Restaurante;

@Component
public class RestauranteInputDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomainObject(RestauranteInput restauranteInput) {
        return modelMapper.map(restauranteInput, Restaurante.class);
    }

    public void copyToDomainObject(RestauranteInput restauranteInput, Restaurante restaurante) {
        // Para evitar org.hibernate.HibernateException: ...model.Cozinha was altered
        // from 1 to 2
        restaurante.setCozinha(new Cozinha());

        modelMapper.map(restauranteInput, restaurante);
    }
}
