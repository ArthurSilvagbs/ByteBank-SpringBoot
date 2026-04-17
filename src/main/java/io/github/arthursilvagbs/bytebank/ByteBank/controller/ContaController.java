package io.github.arthursilvagbs.bytebank.ByteBank.controller;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.conta.ContaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.conta.ContaUpdateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.mappers.ContaMapper;
import io.github.arthursilvagbs.bytebank.ByteBank.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("contas")
@RequiredArgsConstructor
@Tag(name = "Contas", description = "Operações relacionadas à contas")
public class ContaController {

   private final ContaService service;
   private final ContaMapper mapper;

   @Operation(description = "Criar uma conta")
   @ApiResponses(
      value = {
         @ApiResponse(
            responseCode = "201",
            description = "Conta criada com sucesso",
            content = @Content
         ),
         @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos na request"
         )
      }
   )
   @PostMapping
   public ResponseEntity<Object> salvar(@RequestBody ContaCreateDTO dto) {

      Conta conta = service.salvar(dto);

      URI location = ServletUriComponentsBuilder
         .fromCurrentRequest()
         .path("/{id}")
         .buildAndExpand(conta.getId())
         .toUri();

      return ResponseEntity.created(location).build();
   }

   @Operation(description = "Buscar uma conta")
   @ApiResponses(
      value = {
         @ApiResponse(
            responseCode = "200",
            description = "Conta encontrada com sucesso",
            content = @Content(
               mediaType = "application/json",
               schema = @Schema(
                  implementation = ContaResponseDTO.class
               ),
               examples = @ExampleObject(
                  name = "Exemplo de retorno",
                  value = "{\"id\": \"123e4567-e89b-12d3-a456-426614174000\", \"numeroConta\": \"12345\", \"saldo\": \"1000.00\", \"idCliente\": \"123e4567-e89b-12d3-a456-426614174000\"}"
               )
            )
         ),
         @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos na request"
         ),
         @ApiResponse(
            responseCode = "404",
            description = "Conta não encontrada"
         )
      }
   )
   @GetMapping("{id}")
   public ResponseEntity<?> obterPorId(@PathVariable("id") String id) {
      return ResponseEntity.ok(mapper.mapearParaResponse(service.obterPorID(id)));
   }

   @Operation(description = "Atualizar saldo da conta")
   @ApiResponses(
      value = {
         @ApiResponse(
            responseCode = "200",
            description = "Saldo atualizado com sucesso.",
            content = @Content(
               mediaType = "application/json",
               schema = @Schema(implementation = ContaResponseDTO.class),
               examples = @ExampleObject(
                  name = "Exemplo de retorno",
                  value = "{ \"id\": \"123e4567-e89b-12d3-a456-426614174000\", \"saldo\": \"1100\" }"
               )
            )
         ),
         @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos na request"
         ),
         @ApiResponse(
            responseCode = "404",
            description = "Conta não encontrada"
         )
      }
   )
   @PutMapping("{id}")
   public ResponseEntity<Object> atualizarSaldo(@PathVariable("id") String id, @RequestBody ContaUpdateDTO dto) {
      service.atualizar(id, dto);
      return ResponseEntity.ok().build();
   }

   @Operation(description = "Encerrar uma conta")
   @ApiResponses(
      value = {
         @ApiResponse(
            responseCode = "204",
            description = "Deletado com sucesso",
            content = @Content
         ),
         @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos na request"
         ),
         @ApiResponse(
            responseCode = "404",
            description = "Conta não encontrada"
         )
      }
   )
   @DeleteMapping("{id}")
   public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
      service.deletar(id);
      return ResponseEntity.noContent().build();
   }
}
