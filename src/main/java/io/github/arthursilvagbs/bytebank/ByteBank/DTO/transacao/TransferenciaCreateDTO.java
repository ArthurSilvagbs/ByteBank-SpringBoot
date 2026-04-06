package io.github.arthursilvagbs.bytebank.ByteBank.DTO.transacao;

import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;

import java.math.BigDecimal;
import java.util.UUID;

public record TransferenciaCreateDTO(BigDecimal valor, UUID IdContaOrigem, UUID IdContaDestino) {
}
