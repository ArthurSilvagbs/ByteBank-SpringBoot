package io.github.arthursilvagbs.bytebank.ByteBank.controller;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.transacao.TransacaoCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.transacao.TransferenciaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.mappers.TransacaoMapper;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Transacao;
import io.github.arthursilvagbs.bytebank.ByteBank.service.ContaService;
import io.github.arthursilvagbs.bytebank.ByteBank.service.TransacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("transacoes")
@RequiredArgsConstructor
@Tag(name = "Transações", description = "Operações relacionadas a transações")
public class TransacaoController {

   private final TransacaoService transacaoService;
   private final ContaService contaService;

   private final TransacaoMapper mapper;

   public ResponseEntity<?> saque(@RequestBody TransacaoCreateDTO dto) {

      Transacao transacao = transacaoService.saque(dto);

      URI location = ServletUriComponentsBuilder
         .fromCurrentRequest()
         .path("/{id}")
         .buildAndExpand(transacao.getId())
         .toUri();

      return ResponseEntity.created(location).build();
   }

   public ResponseEntity<?> deposito(@RequestBody TransacaoCreateDTO dto) {

      Transacao transacao = transacaoService.deposito(dto);

      URI location = ServletUriComponentsBuilder
         .fromCurrentRequest()
         .path("/{id}")
         .buildAndExpand(transacao.getId())
         .toUri();

      return ResponseEntity.created(location).build();
   }

   public ResponseEntity<?> transferencia(@RequestBody TransferenciaCreateDTO dto) {

      Transacao transacao = transacaoService.transferencia(dto);

      URI location = ServletUriComponentsBuilder
         .fromCurrentRequest()
         .path("/{id}")
         .buildAndExpand(transacao.getId())
         .toUri();

      return ResponseEntity.created(location).build();
   }



}
