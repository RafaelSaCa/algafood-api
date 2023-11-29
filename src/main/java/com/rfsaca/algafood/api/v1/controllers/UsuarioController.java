package com.rfsaca.algafood.api.v1.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rfsaca.algafood.api.v1.assembler.UsuarioDtoAssembler;
import com.rfsaca.algafood.api.v1.assembler.UsuarioInputDisassembler;
import com.rfsaca.algafood.api.v1.model.UsuarioDto;
import com.rfsaca.algafood.api.v1.model.input.SenhaInput;
import com.rfsaca.algafood.api.v1.model.input.UsuarioComSenhaInput;
import com.rfsaca.algafood.api.v1.model.input.UsuarioInput;
import com.rfsaca.algafood.domain.models.Usuario;
import com.rfsaca.algafood.domain.repositories.UsuarioRepository;
import com.rfsaca.algafood.domain.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioDtoAssembler usuarioDtoAssembler;

    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;

    @GetMapping
    public CollectionModel<UsuarioDto> listar() {
        List<Usuario> todosUsuarios = usuarioRepository.findAll();

        return usuarioDtoAssembler.toCollectionModel(todosUsuarios);
    }

    @GetMapping("/{usuarioId}")
    public UsuarioDto buscar(@PathVariable Long usuarioId) {
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);

        return usuarioDtoAssembler.toDto(usuario);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDto salvar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
        usuario = usuarioService.salvar(usuario);

        return usuarioDtoAssembler.toDto(usuario);
    }

    @PutMapping("/{usuarioId}")
    public UsuarioDto atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioAtual = usuarioService.buscarOuFalhar(usuarioId);
        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
        usuarioAtual = usuarioService.salvar(usuarioAtual);

        return usuarioDtoAssembler.toDto(usuarioAtual);
    }

    @PutMapping("/{usuarioId}/senha")
    public void alterarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha) {
        usuarioService.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getNovaSenha());
    }
}
