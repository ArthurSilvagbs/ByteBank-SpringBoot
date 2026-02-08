package io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente;

import io.github.arthursilvagbs.bytebank.ByteBank.model.PessoaJuridica;
import io.github.arthursilvagbs.bytebank.ByteBank.model.TipoCliente;

public record PessoaJuridicaCreateDTO(String nome, String email, String telefone, String endereco, String cnpj) {

    public PessoaJuridica mapearParaPessoaJuridica() {
        return new PessoaJuridica(this.nome, this.email, this.telefone, this.endereco, TipoCliente.PESSOA_JURIDICCA, this.cnpj);
    }
}
