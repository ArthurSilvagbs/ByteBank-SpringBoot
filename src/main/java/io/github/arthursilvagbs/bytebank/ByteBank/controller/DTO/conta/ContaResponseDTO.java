package io.github.arthursilvagbs.bytebank.ByteBank.controller.DTO.conta;

import java.math.BigDecimal;
import java.util.UUID;

public record ContaResponseDTO(UUID id, Integer numeroConta, BigDecimal saldo, UUID idCliente) {
}
