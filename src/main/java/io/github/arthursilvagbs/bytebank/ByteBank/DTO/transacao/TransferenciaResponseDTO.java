package io.github.arthursilvagbs.bytebank.ByteBank.DTO.transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransferenciaResponseDTO(UUID id, UUID idContaOrigem, UUID idContaDestino, BigDecimal valor, LocalDateTime dataHora) {
}
