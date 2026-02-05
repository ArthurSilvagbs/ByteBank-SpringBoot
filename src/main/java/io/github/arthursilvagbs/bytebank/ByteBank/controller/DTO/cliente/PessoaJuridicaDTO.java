package io.github.arthursilvagbs.bytebank.ByteBank.controller.DTO.cliente;

import io.github.arthursilvagbs.bytebank.ByteBank.model.PessoaJuridica;
import io.github.arthursilvagbs.bytebank.ByteBank.model.TipoCliente;

public record PessoaJuridicaDTO(String nome, String email, String telefone, String endereco, TipoCliente tipoCliente, String cnpj) {

    public PessoaJuridica mapearParaPessoaJuridica() {
        return new PessoaJuridica(this.nome, this.email, this.telefone, this.endereco, this.tipoCliente, this.cnpj);
    }
}
