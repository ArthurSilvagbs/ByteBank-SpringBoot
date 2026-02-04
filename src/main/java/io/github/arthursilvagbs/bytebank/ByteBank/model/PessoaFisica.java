package io.github.arthursilvagbs.bytebank.ByteBank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pessoa_fisica")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class PessoaFisica extends Cliente {

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    public PessoaFisica(){}

    public PessoaFisica(String nome, String email, String telefone, String endereco, TipoCliente tipoCliente, String cpf) {
        super(nome, email, telefone, endereco, TipoCliente.PESSOA_FISICA);
        this.cpf = cpf;
    }
}
