package io.github.arthursilvagbs.bytebank.ByteBank.mappers;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.PessoaFisicaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.PessoaJuridicaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.model.PessoaFisica;
import io.github.arthursilvagbs.bytebank.ByteBank.model.PessoaJuridica;
import io.github.arthursilvagbs.bytebank.ByteBank.model.TipoCliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

   public Cliente mapearParaPessoaFisica(PessoaFisicaCreateDTO dto) {
      return new PessoaFisica(dto.nome(), dto.email(), dto.telefone(), dto.endereco(), TipoCliente.PESSOA_FISICA, dto.cpf());
   }

   public Cliente mapearParaPessoaJuridica(PessoaJuridicaCreateDTO dto) {
      return new PessoaJuridica(dto.nome(), dto.email(), dto.telefone(), dto.endereco(), TipoCliente.PESSOA_JURIDICCA, dto.cnpj());
   }
}
