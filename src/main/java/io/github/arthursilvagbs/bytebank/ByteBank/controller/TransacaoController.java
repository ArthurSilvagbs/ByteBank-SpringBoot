package io.github.arthursilvagbs.bytebank.ByteBank.controller;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.transacao.TransacaoCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.transacao.TransferenciaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.mappers.TransacaoMapper;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Transacao;
import io.github.arthursilvagbs.bytebank.ByteBank.service.ContaService;
import io.github.arthursilvagbs.bytebank.ByteBank.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("transacoes")
@RequiredArgsConstructor
@Tag(name = "Transações", description = "Operações relacionadas a transações")
public class TransacaoController {

   private final TransacaoService transacaoService;
   private final ContaService contaService;

   private final TransacaoMapper mapper;

   @Operation(description = "Efetuar um saque")
   @ApiResponses(
      value = {
         @ApiResponse(
            responseCode = "201",
            description = "Saque efetuado com sucesso",
            content = @Content
         ),
         @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos na request"
         )
      }
   )
   @PostMapping("/saque")
   public ResponseEntity<?> saque(@RequestBody TransacaoCreateDTO dto) {

      Transacao transacao = transacaoService.saque(dto);

      URI location = ServletUriComponentsBuilder
         .fromCurrentRequest()
         .path("/{id}")
         .buildAndExpand(transacao.getId())
         .toUri();

      return ResponseEntity.created(location).build();
   }

   @Operation(description = "Efetuar um deposito")
   @ApiResponses(
      value = {
         @ApiResponse(
            responseCode = "201",
            description = "Deposito efetuado com sucesso",
            content = @Content
         ),
         @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos na request"
         )
      }
   )
   @PostMapping("/deposito")
   public ResponseEntity<?> deposito(@RequestBody TransacaoCreateDTO dto) {

      Transacao transacao = transacaoService.deposito(dto);

      URI location = ServletUriComponentsBuilder
         .fromCurrentRequest()
         .path("/{id}")
         .buildAndExpand(transacao.getId())
         .toUri();

      return ResponseEntity.created(location).build();
   }

   @Operation(description = "Efetuar uma transferência")
   @ApiResponses(
      value = {
         @ApiResponse(
            responseCode = "201",
            description = "Transferência efetuada com sucesso",
            content = @Content
         ),
         @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos na request"
         )
      }
   )
   @PostMapping("/transferencia")
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
