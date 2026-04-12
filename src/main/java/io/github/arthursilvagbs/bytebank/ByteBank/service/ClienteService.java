package io.github.arthursilvagbs.bytebank.ByteBank.service;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.PessoaFisicaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.PessoaJuridicaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.OperacaoNaoPermitidaException;
import io.github.arthursilvagbs.bytebank.ByteBank.mappers.ClienteMapper;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.model.TipoCliente;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.ClienteRepository;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {

   private final ClienteRepository respository;
   private final ContaRepository contaRepository;
   private final ClienteMapper mapper;

   public Cliente salvarPessoaFisica(PessoaFisicaCreateDTO dto) {
      Cliente cliente = mapper.mapearParaPessoaFisica(dto);
      return respository.save(cliente);
   }

   public Cliente salvarPessoaJuridica(PessoaJuridicaCreateDTO dto) {
      Cliente cliente = mapper.mapearParaPessoaJuridica(dto);
      return respository.save(cliente);
   }

   public void atualizar(Cliente cliente) {
      if (cliente.getId() == null) {
         throw new IllegalArgumentException("Para atualizar um cliente, é ncessário que ele esteja cadastrado.");
      }
      respository.save(cliente);
   }

   public Optional<Cliente> obterPorId(UUID id) {
      return respository.findById(id);
   }

   public void deletar(Cliente cliente) {
      if (possuiConta(cliente)) {
         throw new OperacaoNaoPermitidaException("O cliente não pode ser deletado, pois ainda existem contas vinculadas a ele.");
      }
      respository.delete(cliente);
   }

   public Optional<Cliente> pesquisaPorNome(String nome) {
      return respository.findByNome(nome);
   }

   public Optional<Cliente> pesquisaPorId(UUID id) {
      return respository.findById(id);
   }

   public List<Cliente> obterTodosOsClientes() {
      return respository.findAll();
   }

   public boolean possuiConta(Cliente cliente) {
      return contaRepository.existsByCliente(cliente);
   }

}
