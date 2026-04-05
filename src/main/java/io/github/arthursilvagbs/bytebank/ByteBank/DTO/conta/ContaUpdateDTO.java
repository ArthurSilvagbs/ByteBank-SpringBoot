package io.github.arthursilvagbs.bytebank.ByteBank.DTO.conta;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ContaUpdateDTO(

   @Schema(description = "Saldo da conta", example = "1000")
   BigDecimal saldo
) {
}
