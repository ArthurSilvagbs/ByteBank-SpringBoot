package io.github.arthursilvagbs.bytebank.ByteBank.DTO.transacao;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransferenciaResponseDTO(

   @Schema(
      description = "Id da trasferência",
      example = "123e4567-e89b-12d3-a456-426614174000"
   )
   UUID id,

   @Schema(
      description = "Id da conta origem da transferência",
      example = "123e4567-e89b-12d3-a456-426614174000"
   )
   UUID idContaOrigem,

   @Schema(
      description = "Id da conta destino da transferência",
      example = "123e4567-e89b-12d3-a456-426614174000"
   )
   UUID idContaDestino,

   @Schema(
      description = "Valor da transferência",
      example = "1000.00"
   )
   BigDecimal valor,

   @Schema(
      description = "Data e hora que a transferência foi efetuada",
      example = "2024-03-15T14:30:00"
   )
   LocalDateTime dataHora
) {
}
