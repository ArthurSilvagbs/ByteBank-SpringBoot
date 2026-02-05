package io.github.arthursilvagbs.bytebank.ByteBank.controller.DTO.movimentacao;

import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Movimentacao;
import io.github.arthursilvagbs.bytebank.ByteBank.model.TipoMovimentacao;

import java.math.BigDecimal;

public record MovimentacaoCreateDTO(BigDecimal valor, TipoMovimentacao tipoMovimentacao, Conta contaOrigem, Conta contaDestino) {

    public Movimentacao mapearParaMovimentacao(){
        return new Movimentacao(this.valor, this.tipoMovimentacao, this.contaOrigem, this.contaDestino);
    }
}
