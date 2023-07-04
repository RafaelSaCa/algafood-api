package com.rfsaca.algafood.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rfsaca.algafood.domain.exceptions.RestauranteNaoEncontradoException;
import com.rfsaca.algafood.domain.models.Restaurante;
import com.rfsaca.algafood.domain.repositories.RestauranteRepository;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

    @Transactional
    public void excluir(Long restauranteId) {
        try {
            restauranteRepository.deleteById(restauranteId);
        } catch (EmptyResultDataAccessException e) {
            throw new RestauranteNaoEncontradoException(restauranteId);
        }
    }

    public Restaurante buscarOuFalhar(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
    }
}
