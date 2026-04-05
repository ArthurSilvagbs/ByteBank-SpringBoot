package io.github.arthursilvagbs.bytebank.ByteBank.controller;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.PessoaFisicaResponseDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.conta.ContaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.conta.ContaResponseDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.conta.ContaUpdateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import io.github.arthursilvagbs.bytebank.ByteBank.service.ClienteService;
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
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("contas")
@RequiredArgsConstructor
@Tag(name = "Contas", description = "Operações relacionadas à contas")
public class ContaController {

   private final ContaService service;
   private final ClienteService clienteService;

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
   public ResponseEntity<Object> salvar(@RequestBody String idCliente) {
      UUID id = UUID.fromString(idCliente);
      Optional<Cliente> cliente = clienteService.obterPorId(id);

      if (cliente.isEmpty()) {
         System.out.println("Cliente não encontrado.");
         return ResponseEntity.notFound().build();
      }

      ContaCreateDTO dto = new ContaCreateDTO(cliente.get());
      Conta conta = dto.mapearParaCliente();

      service.salvar(conta);

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
      UUID idConta = UUID.fromString(id);
      Optional<Conta> contaOptional = service.obterPorID(idConta);

      if (contaOptional.isEmpty()) {
         return ResponseEntity.notFound().build();
      }

      Conta conta = contaOptional.get();

      ContaResponseDTO dto = new ContaResponseDTO(
         conta.getId(),
         conta.getNumeroConta(),
         conta.getSaldo(),
         conta.getCliente().getId());

      return ResponseEntity.ok(dto);
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
   public ResponseEntity<Object> atualizarSaldo(@PathVariable("id") String id, ContaUpdateDTO dto) {
      UUID idConta = UUID.fromString(id);
      Optional<Conta> contaOptional = service.obterPorID(idConta);

      if (contaOptional.isEmpty()) {
         return ResponseEntity.notFound().build();
      }

      Conta contaEntidade = contaOptional.get();

      contaEntidade.setSaldo(dto.saldo());

      service.atualizar(contaEntidade);

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
      UUID idConta = UUID.fromString(id);
      Optional<Conta> contaEncontrado = service.obterPorID(idConta);

      if (contaEncontrado.isEmpty()) {
         return ResponseEntity.notFound().build();
      }

      service.deletar(contaEncontrado.get());

      return ResponseEntity.noContent().build();
   }
}
