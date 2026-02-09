package io.github.arthursilvagbs.bytebank.ByteBank.DTO.conta;

import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;

import java.util.UUID;

public record ContaCreateDTO(UUID idCliente) {

//    public Conta mapearParaCliente() {
//        return new Conta(this.idCliente);
//    }
}
