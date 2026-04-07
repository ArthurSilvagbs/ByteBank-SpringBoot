package io.github.arthursilvagbs.bytebank.ByteBank.model;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.PessoaFisicaCreateDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pessoa_fisica")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@NoArgsConstructor
public class PessoaFisica extends Cliente {

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    public PessoaFisica(String nome, String email, String telefone, String endereco, TipoCliente tipoCliente, String cpf) {
        super(nome, email, telefone, endereco, TipoCliente.PESSOA_FISICA);
        this.cpf = cpf;
    }

   public PessoaFisica(PessoaFisicaCreateDTO dto) {
      super(dto.nome(), dto.email(), dto.telefone(), dto.endereco(), TipoCliente.PESSOA_FISICA);
      this.cpf = dto.cpf();
   }
}
