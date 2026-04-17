package io.github.arthursilvagbs.bytebank.ByteBank.mappers;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.conta.ContaResponseDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import org.springframework.stereotype.Component;

@Component
public class ContaMapper {

   public Conta mapearParaConta(Cliente cliente) {
      return new Conta(cliente);
   }

   public ContaResponseDTO mapearParaResponse(Conta conta) {
      return new ContaResponseDTO(
         conta.getId(),
         conta.getNumeroConta(),
         conta.getSaldo(),
         conta.getCliente().getId()
      );
   }
}
