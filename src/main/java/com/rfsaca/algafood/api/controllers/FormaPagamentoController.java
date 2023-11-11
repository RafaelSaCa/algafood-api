package com.rfsaca.algafood.api.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rfsaca.algafood.api.assembler.FormaPagamentoDtoAssembler;
import com.rfsaca.algafood.api.assembler.FormaPagamentoInputDisassembler;
import com.rfsaca.algafood.api.model.FormaPagamentoDto;
import com.rfsaca.algafood.api.model.input.FormaPagamentoInput;
import com.rfsaca.algafood.domain.models.FormaPagamento;
import com.rfsaca.algafood.domain.repositories.FormaPagamentoRepository;
import com.rfsaca.algafood.domain.services.FormaPagamentoService;

@RestController
@RequestMapping("/{formas-pagamento}")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private FormaPagamentoDtoAssembler formaPagamentoDtoAssembler;

    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;

    // @GetMapping
    // public List<FormaPagamentoDto> listar() {
    // List<FormaPagamento> todasFormasPagamentos =
    // formaPagamentoRepository.findAll();

    // return formaPagamentoDtoAssembler.toCollectionDto(todasFormasPagamentos);
    // }

    // ADD CACHE-CONTROL
    @GetMapping
    public ResponseEntity<List<FormaPagamentoDto>> listar() {
        List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.findAll();

        List<FormaPagamentoDto> formasPagamentosDtos = formaPagamentoDtoAssembler
                .toCollectionDto(todasFormasPagamentos);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(formasPagamentosDtos);

    }

    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoDto buscar(@PathVariable Long formaPagamentoId) {
        FormaPagamento formaPagamento = formaPagamentoService.buscarOuFalhar(formaPagamentoId);

        return formaPagamentoDtoAssembler.toDto(formaPagamento);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDto adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);

        formaPagamento = formaPagamentoService.salvar(formaPagamento);
        return formaPagamentoDtoAssembler.toDto(formaPagamento);
    }

    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoDto atualizar(@PathVariable Long formaPagamentoId,
            @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamentoAtual = formaPagamentoService.buscarOuFalhar(formaPagamentoId);

        formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);

        return formaPagamentoDtoAssembler.toDto(formaPagamentoAtual);
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long formaPagamentoId) {
        formaPagamentoService.excluir(formaPagamentoId);
    }
}
