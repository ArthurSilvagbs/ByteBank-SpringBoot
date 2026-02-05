package io.github.arthursilvagbs.bytebank.ByteBank.controller.DTO.conta;

import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;

public record ContaDTO(Cliente cliente) {

    public Conta mapearParaCliente() {
        return new Conta(this.cliente);
    }
}
