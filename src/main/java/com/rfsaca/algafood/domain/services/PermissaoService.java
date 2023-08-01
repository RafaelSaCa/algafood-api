package com.rfsaca.algafood.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rfsaca.algafood.domain.exceptions.PermissaoNaoEncontradaException;
import com.rfsaca.algafood.domain.models.Permissao;
import com.rfsaca.algafood.domain.repositories.PermissaoRepository;

@Service
public class PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao buscarOuFalhar(Long permissaoId) {
        return permissaoRepository.findById(permissaoId)
                .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
    }
}
