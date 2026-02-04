package io.github.arthursilvagbs.bytebank.ByteBank.model;

import jakarta.persistence.*;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "pessoa_fisica")
@PrimaryKeyJoinColumn(name = "id")
public class PessoaFisica extends Cliente {

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;
}
