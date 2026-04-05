package io.github.arthursilvagbs.bytebank.ByteBank.DTO.conta;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

public record ContaResponseDTO(

   @Schema(description = "ID da conta", example = "123e4567-e89b-12d3-a456-426614174000")
   UUID id,

   @Schema(description = "Número da conta", example = "12345")
   Integer numeroConta,

   @Schema(description = "Saldo da conta", example = "1000.00")
   BigDecimal saldo,

   @Schema(description = "ID do cliente titular da conta", example = "321e4567-e89b-12d3-a456-426614174000")
   UUID idCliente) {
}
