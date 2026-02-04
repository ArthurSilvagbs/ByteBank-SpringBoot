package io.github.arthursilvagbs.bytebank.ByteBank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "pessoa_juridica")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@ToString
public class PessoaJuridica extends Cliente {

    @Column(name = "cnpj", nullable = false, unique = true, length = 14)
    private String cnpj;
}
