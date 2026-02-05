package io.github.arthursilvagbs.bytebank.ByteBank.controller;

import io.github.arthursilvagbs.bytebank.ByteBank.controller.DTO.movimentacao.MovimentacaoCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Movimentacao;
import io.github.arthursilvagbs.bytebank.ByteBank.service.ContaService;
import io.github.arthursilvagbs.bytebank.ByteBank.service.MovimentacaoService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
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
public class MovimentacaoController {

    private final MovimentacaoService service;
    private final ContaService contaService;

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
