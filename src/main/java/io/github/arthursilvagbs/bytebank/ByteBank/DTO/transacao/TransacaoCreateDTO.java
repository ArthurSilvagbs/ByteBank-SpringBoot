package io.github.arthursilvagbs.bytebank.ByteBank.DTO.transacao;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record TransacaoCreateDTO (

   @Schema(
      description = "Valor da transação, seja saque ou deposito",
      example = "1000.00"
   )
   @NotNull(message = "O campo é obrigatório")
   @Positive(message = "O valor deve ser positivo")
   BigDecimal valor,

   @Schema(
      description = "Id da conta que a opeção (saque ou deposito) vai ser efetuada",
      example = "123e4567-e89b-12d3-a456-426614174000"
   )
   @NotNull(message = "O campo é obrigatório")
   UUID IdContaOrigem
) {
}
