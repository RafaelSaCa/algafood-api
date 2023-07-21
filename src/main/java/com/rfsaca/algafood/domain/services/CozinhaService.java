package com.rfsaca.algafood.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rfsaca.algafood.domain.exceptions.CozinhaNaoEncontradaException;
import com.rfsaca.algafood.domain.exceptions.EntidadeEmUsoException;
import com.rfsaca.algafood.domain.models.Cozinha;
import com.rfsaca.algafood.domain.repositories.CozinhaRepository;

@Service
public class CozinhaService {

    private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";

    @Autowired
    private CozinhaRepository cozinhaRepositoy;

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return cozinhaRepositoy.save(cozinha);

    }

    @Transactional
    public void excluir(Long cozinhaId) {

        try {
            cozinhaRepositoy.deleteById(cozinhaId);
            cozinhaRepositoy.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new CozinhaNaoEncontradaException(cozinhaId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_COZINHA_EM_USO, cozinhaId));
        }
    }

    public Cozinha buscarOuFalhar(Long cozinhaId) {
        return cozinhaRepositoy.findById(cozinhaId)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(cozinhaId));

    }
}
