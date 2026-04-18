package io.github.arthursilvagbs.bytebank.ByteBank.DTO.conta;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ContaUpdateDTO(

   @Schema(description = "Saldo da conta", example = "1000")
   @NotNull(message = "Campo obrigatório")
   @Positive(message = "O valor precisa ser positivo")
   BigDecimal saldo
) {
}
