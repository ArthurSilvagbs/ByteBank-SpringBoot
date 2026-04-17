package io.github.arthursilvagbs.bytebank.ByteBank.controller;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.*;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import io.github.arthursilvagbs.bytebank.ByteBank.model.PessoaFisica;
import io.github.arthursilvagbs.bytebank.ByteBank.model.PessoaJuridica;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Operações relacionadas à clientes")
public class ClienteController {

   private final ClienteService service;

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
   public ResponseEntity<?> buscarPorId(@PathVariable("id") String id) {

      UUID uuid = UUID.fromString(id);

      Optional<Cliente> clienteEncontrado = service.obterPorId(uuid);

      if (clienteEncontrado.isPresent()) {
         Cliente clienteEntidade = clienteEncontrado.get();

         if (clienteEntidade instanceof PessoaFisica pf) {
            PessoaFisicaResponseDTO pessoaFisicaResponseDTO = new PessoaFisicaResponseDTO(
               pf.getId(),
               pf.getNome(),
               pf.getEmail(),
               pf.getTelefone(),
               pf.getEndereco(),
               pf.getTipoCliente(),
               pf.getCpf()
            );

            return ResponseEntity.ok(pessoaFisicaResponseDTO);
         }

         if (clienteEntidade instanceof PessoaJuridica pj) {
            PessoaJuridicaResponseDTO pessoaJuridicaResponseDTO = new PessoaJuridicaResponseDTO(
               pj.getId(),
               pj.getNome(),
               pj.getEmail(),
               pj.getTelefone(),
               pj.getEndereco(),
               pj.getTipoCliente(),
               pj.getCnpj()
            );

            return ResponseEntity.ok(pessoaJuridicaResponseDTO);
         }

      }

      return ResponseEntity.notFound().build();
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
   public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody ClienteUpdateDTO dto) {
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
   public ResponseEntity<Object> deletarV1(@PathVariable("id") String id) {
      service.deletar(id);
      return ResponseEntity.noContent().build();
   }

}
