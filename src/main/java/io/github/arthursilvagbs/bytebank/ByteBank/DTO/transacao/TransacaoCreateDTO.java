package io.github.arthursilvagbs.bytebank.ByteBank.DTO.transacao;

import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Transacao;

import java.math.BigDecimal;
import java.util.UUID;

public record TransacaoCreateDTO (BigDecimal valor, UUID IdContaOrigem) {


   public Transacao mapearParaTransacao(BigDecimal valor, Conta contaOrigem){
      return new Transacao(contaOrigem, valor);
   }
}
