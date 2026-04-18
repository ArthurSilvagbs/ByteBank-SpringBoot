package io.github.arthursilvagbs.bytebank.ByteBank.DTO.conta;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ContaCreateDTO(
   @Schema(
      description = "Id do cliente titular da conta",
      example = "123e4567-e89b-12d3-a456-426614174000"
   )
   @NotNull(message = "Campo obrigatório")
   UUID clienteId
) { }
