package com.rfsaca.algafood.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rfsaca.algafood.api.assembler.CozinhaDtoAssembler;
import com.rfsaca.algafood.api.assembler.CozinhaInputDisassembler;
import com.rfsaca.algafood.api.model.CozinhaDto;
import com.rfsaca.algafood.api.model.input.CozinhaInput;
import com.rfsaca.algafood.domain.models.Cozinha;
import com.rfsaca.algafood.domain.repositories.CozinhaRepository;
import com.rfsaca.algafood.domain.services.CozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;
    @Autowired
    private CozinhaService cozinhaService;
    @Autowired
    private CozinhaDtoAssembler cozinhaDtoAssembler;
    @Autowired
    private CozinhaInputDisassembler cozinhaInputDisassembler;

    @GetMapping
    public List<CozinhaDto> listar() {
        List<Cozinha> todasCozinhas = cozinhaRepository.findAll();
        return cozinhaDtoAssembler.toCollectionDto(todasCozinhas);
    }

    @GetMapping("/{cozinhaId}")
    public CozinhaDto buscar(@PathVariable Long cozinhaId) {
        Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
        return cozinhaDtoAssembler.toDto(cozinha);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaDto adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
        cozinha = cozinhaService.salvar(cozinha);

        return cozinhaDtoAssembler.toDto(cozinha);
    }

    @PutMapping("/{cozinhaId}")
    public CozinhaDto atualizar(@PathVariable Long cozinhaId, @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaAtual = cozinhaService.buscarOuFalhar(cozinhaId);

        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
        cozinhaAtual = cozinhaService.salvar(cozinhaAtual);

        return cozinhaDtoAssembler.toDto(cozinhaAtual);
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long cozinhaId) {
        cozinhaService.excluir(cozinhaId);

    }

}
