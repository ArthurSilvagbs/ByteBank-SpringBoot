package io.github.arthursilvagbs.bytebank.ByteBank.controller;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.*;
import io.github.arthursilvagbs.bytebank.ByteBank.mappers.ClienteMapper;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Operações relacionadas à clientes")
public class ClienteController {

   private final ClienteService service;
   private final ClienteMapper mapper;

   @Operation(description = "Criar um cliente Pessoa Física")
   @ApiResponses(
      value = {
         @ApiResponse(
            responseCode = "201",
            description = "Cliente Pessoa Física criada com sucesso.",
            content = @Content
         ),
         @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos na request")
      }
   )
   @PostMapping("/pf")
   @PreAuthorize("hasAnyHole('ADMIN')")
   public ResponseEntity<Object> salvarPessoaFisica(@RequestBody @Valid PessoaFisicaCreateDTO dto) {

      Cliente cliente = service.salvarPessoaFisica(dto);

      URI location = ServletUriComponentsBuilder
         .fromCurrentRequest()
         .path("/{id}")
         .buildAndExpand(cliente.getId())
         .toUri();

      return ResponseEntity.created(location).build();
   }

   @Operation(description = "Criar um cliente Pessoa Juridica")
   @ApiResponses(
      value = {
         @ApiResponse(
            responseCode = "201",
            description = "Cliente Pessoa Jurídica criada com sucesso.",
            content = @Content
         ),
         @ApiResponse(responseCode = "400", description = "Dados inválidos na request")
      }
   )
   @PostMapping("/pj")
   @PreAuthorize("hasAnyHole('ADMIN')")
   public ResponseEntity<Object> salvarPessoaJuridica(@RequestBody @Valid PessoaJuridicaCreateDTO dto) {

      Cliente cliente = service.salvarPessoaJuridica(dto);

      URI location = ServletUriComponentsBuilder
         .fromCurrentRequest()
         .path("/{id}")
         .buildAndExpand(cliente.getId())
         .toUri();

      return ResponseEntity.created(location).build();
   }

   @Operation(description = "Buscar um cliente no banco via ID")
   @ApiResponses(
      value = {
         @ApiResponse(
            responseCode = "200",
            description = "Retorna o cliente encontrado no body da response",
            content = @Content(
               mediaType = "application/json",
               schema = @Schema(
                  oneOf = {PessoaFisicaResponseDTO.class, PessoaJuridicaResponseDTO.class},
                  description = "Pode retornar Pessoa Física ou Pessoa Jurídica"
               ),
               examples = @ExampleObject(
                  name = "Exemplo de retorno",
                  value = "{\"id\": \"123e4567-e89b-12d3-a456-426614174000\", \"nome\": \"João Silva\", \"email\": \"joao@email.com\"}"
               )
            )
         ),
         @ApiResponse(responseCode = "400", description = "Dados inválidos na request"),
         @ApiResponse(responseCode = "404", description = "ID informado não foi encontrado no banco de dados")
      }
   )
   @GetMapping("{id}")
   @PreAuthorize("hasAnyHole('ADMIN')")
   public ResponseEntity<?> buscarPorId(@PathVariable("id") String id) {
      Cliente cliente = service.obterPorId(id);
      return ResponseEntity.ok(mapper.mapearParaResponse(cliente));
   }

   @Operation(description = "Atualizar um cliente existente no banco de dados, buscando-o via ID")
   @ApiResponses(
      value = {
         @ApiResponse(
            responseCode = "200",
            description = "Dados do cliente atualizados com sucesso.",
            content = @Content(
               mediaType = "application/json",
               schema = @Schema(oneOf = {PessoaFisicaResponseDTO.class, PessoaJuridicaResponseDTO.class}),
               examples = @ExampleObject(
                  name = "Exemplo de retorno",
                  value = "{ \"id\": \"123e4567-e89b-12d3-a456-426614174000\", \"nome\": \"João Silva\", \"email\": \"joao@email.com\", \"telefone\": \"00912345678\", \"endereco\": \"Rua das Palmeiras, nº 452, Bairro Jardim Alvorada, Brasília\" }"
               )
            )
         ),
         @ApiResponse(responseCode = "400", description = "Dados inválidos na request"),
         @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
      }
   )
   @PutMapping("{id}")
   @PreAuthorize("hasAnyHole('ADMIN')")
   public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid ClienteUpdateDTO dto) {
      service.atualizar(id, dto);
      return ResponseEntity.ok().build();
   }

   @Operation(description = "Atualizar um cliente existente no banco de dados, buscando-o via ID")
   @ApiResponses(
      value = {
         @ApiResponse(
            responseCode = "204",
            description = "Cliente deletado com sucesso.",
            content = @Content
         ),
         @ApiResponse(responseCode = "400", description = "Dados inválidos na request"),
         @ApiResponse(responseCode = "404", description = "ID do cliente não encontrado no banco de dados, cliente não encontrado")
      }
   )
   @DeleteMapping("{id}")
   @PreAuthorize("hasAnyHole('ADMIN')")
   public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
      service.deletar(id);
      return ResponseEntity.noContent().build();
   }

}
