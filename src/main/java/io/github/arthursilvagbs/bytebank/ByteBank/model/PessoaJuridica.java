package io.github.arthursilvagbs.bytebank.ByteBank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pessoa_juridica")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class PessoaJuridica extends Cliente {

    @Column(name = "cnpj", nullable = false, unique = true, length = 14)
    private String cnpj;

    public PessoaJuridica() {}

    public PessoaJuridica(String nome, String email, String telefone, String endereco, TipoCliente tipoCliente, String cnpj) {
        super(nome, email, telefone, endereco, TipoCliente.PESSOA_FISICA);
        this.cnpj = cnpj;
    }

}
