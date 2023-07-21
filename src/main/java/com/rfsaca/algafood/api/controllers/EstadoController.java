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

import com.rfsaca.algafood.api.assembler.EstadoDtoAssembler;
import com.rfsaca.algafood.api.assembler.EstadoInputDisassembler;
import com.rfsaca.algafood.api.model.EstadoDto;
import com.rfsaca.algafood.api.model.input.EstadoInput;
import com.rfsaca.algafood.domain.models.Estado;
import com.rfsaca.algafood.domain.repositories.EstadoRepository;
import com.rfsaca.algafood.domain.services.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private EstadoService estadoService;
    @Autowired
    private EstadoDtoAssembler estadoDtoAssembler;
    @Autowired
    private EstadoInputDisassembler estadoInputDisassembler;

    @GetMapping
    public List<EstadoDto> listar() {
        List<Estado> todosEstados = estadoRepository.findAll();
        return estadoDtoAssembler.toCollectionDto(todosEstados);
    }

    @GetMapping("/{estadoId}")
    public EstadoDto buscar(@PathVariable Long estadoId) {
        Estado estado = estadoService.buscarOuFalhar(estadoId);

        return estadoDtoAssembler.toDto(estado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDto adicionar(@RequestBody @Valid EstadoInput estadoiInput) {
        Estado estado = estadoInputDisassembler.toDomainObject(estadoiInput);

        estado = estadoService.salvar(estado);

        return estadoDtoAssembler.toDto(estado);
    }

    @PutMapping("/{estadoId}")
    public EstadoDto atualizar(@PathVariable Long estadoId, @RequestBody @Valid EstadoInput estadoInput) {
        Estado estadoAtual = estadoService.buscarOuFalhar(estadoId);

        estadoInputDisassembler.copyToDomainObject(estadoInput, estadoAtual);

        estadoAtual = estadoService.salvar(estadoAtual);

        return estadoDtoAssembler.toDto(estadoAtual);

    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long estadoId) {
        estadoService.excluir(estadoId);
    }
}
