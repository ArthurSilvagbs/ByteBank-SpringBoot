package io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente;

import io.github.arthursilvagbs.bytebank.ByteBank.model.PessoaFisica;
import io.github.arthursilvagbs.bytebank.ByteBank.model.TipoCliente;

public record PessoaFisicaCreateDTO(String nome, String email, String telefone, String endereco, String cpf) {

    public PessoaFisica mapearParaPessoaFisica() {
        return new PessoaFisica(this.nome, this.email, this.telefone, this.endereco, TipoCliente.PESSOA_FISICA, this.cpf);
    }
}
