package io.github.arthursilvagbs.bytebank.ByteBank.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "pessoa_juridica")
@PrimaryKeyJoinColumn(name = "id")
public class PessoaJuridica extends Cliente {

    @Column(name = "cnpj", nullable = false, unique = true, length = 14)
    private String cnpj;
}
