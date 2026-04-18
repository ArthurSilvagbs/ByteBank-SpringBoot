package io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;

public record ClienteUpdateDTO(

   @Schema(description = "Nome do cliente", example = "João Pedro Silva")
   String nome,

   @Schema(description = "Email do cliente", example = "joaosilva@email.com")
   @Email(message = "Deve ser um email válido")
   String email,

   @Schema(description = "Telefone do cliente", example = "00912345678")
   String telefone,

   @Schema(description = "Endereço do cliente", example = "Rua das Palmeiras, nº 452, Bairro Jardim Alvorada, Brasília")
   String endereco
) {
}
