package io.github.arthursilvagbs.bytebank.ByteBank.controller.DTO;

import io.github.arthursilvagbs.bytebank.ByteBank.model.PessoaFisica;
import io.github.arthursilvagbs.bytebank.ByteBank.model.TipoCliente;

public record PessoaFisicaDTO(String nome, String email, String telefone, String endereco, TipoCliente tipoCliente, String cpf) {

    public PessoaFisica mapearParaPessoaFisica() {
        PessoaFisica contaPF = new PessoaFisica()
    }
}
