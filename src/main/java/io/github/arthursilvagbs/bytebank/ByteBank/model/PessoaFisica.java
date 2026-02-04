package io.github.arthursilvagbs.bytebank.ByteBank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "pessoa_fisica")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class PessoaFisica extends Cliente {

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

}
