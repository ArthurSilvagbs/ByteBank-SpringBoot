package io.github.arthursilvagbs.bytebank.ByteBank.DTO.transacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransacaoResponseDTO(UUID id, UUID idContaOrigem, BigDecimal valor, LocalDateTime dataHora) {
}
