package io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente;

import io.github.arthursilvagbs.bytebank.ByteBank.model.TipoCliente;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record   PessoaJuridicaResponseDTO(
   @Schema(description = "ID do cliente", example = "123e4567-e89b-12d3-a456-426614174000")
   UUID id,

   @Schema(description = "Nome do cliente", example = "João Pedro Silva")
   String nome,

   @Schema(description = "Email do cliente", example = "joaosilva@email.com")
   String email,

   @Schema(description = "Telefone do cliente", example = "00912345678")
   String telefone,

   @Schema(description = "Endereço do cliente", example = "Rua das Palmeiras, nº 452, Bairro Jardim Alvorada, Brasília")
   String endereco,

   @Schema(description = "Qual o tipo do cliente", example = "PESSOA_JURIDICA")
   TipoCliente tipoCliente,

   @Schema(description = "CNPJ do cliente", example = "42105938000172")
   String cnpj
) {
}