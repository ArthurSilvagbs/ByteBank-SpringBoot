package io.github.arthursilvagbs.bytebank.ByteBank.mappers;

import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Transacao;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransacaoMapper {

   public Transacao mapearParaTransacao(BigDecimal valor, Conta contaOrigem) {
      return new Transacao(contaOrigem, valor);
   }

   public Transacao mapearParaTransferencia(BigDecimal valor, Conta contaOrigem, Conta contaDestino) {
      return new Transacao(valor, contaOrigem, contaDestino);
   }
}