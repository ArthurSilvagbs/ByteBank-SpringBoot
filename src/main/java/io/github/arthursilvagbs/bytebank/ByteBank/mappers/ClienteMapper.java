package io.github.arthursilvagbs.bytebank.ByteBank.mappers;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.*;
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

   public Object mapearParaResponse(Cliente cliente) {
      if (cliente instanceof PessoaFisica pf) {
         return new PessoaFisicaResponseDTO(
            pf.getId(),
            pf.getNome(),
            pf.getEmail(),
            pf.getTelefone(),
            pf.getEndereco(),
            pf.getTipoCliente(),
            pf.getCpf()
         );
      }

      if (cliente instanceof PessoaJuridica pj) {
         return new PessoaJuridicaResponseDTO(
            pj.getId(),
            pj.getNome(),
            pj.getEmail(),
            pj.getTelefone(),
            pj.getEndereco(),
            pj.getTipoCliente(),
            pj.getCnpj()
         );
      }

      throw new IllegalArgumentException("Tipo de cliente desconhecido");
   }
}
