package io.github.arthursilvagbs.bytebank.ByteBank.controller;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.movimentacao.MovimentacaoCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Movimentacao;
import io.github.arthursilvagbs.bytebank.ByteBank.service.ContaService;
import io.github.arthursilvagbs.bytebank.ByteBank.service.MovimentacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("movimentacoes")
@RequiredArgsConstructor
@Tag(name = "Movimentações",description = "Operações relacionadas à movimentações")
public class MovimentacaoController {

    private final MovimentacaoService service;
    private final ContaService contaService;

    @Operation(description = "Efetuar uma operação")
    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody MovimentacaoCreateDTO movimentacaoDto) {
        Movimentacao movimentacao = movimentacaoDto.mapearParaMovimentacao();

        service.salvar(movimentacao);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(movimentacao.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

}
