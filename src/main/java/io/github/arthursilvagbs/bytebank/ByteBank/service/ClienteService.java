package io.github.arthursilvagbs.bytebank.ByteBank.service;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.ClienteUpdateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.PessoaFisicaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.PessoaJuridicaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.ClienteNaoEcontradoException;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.OperacaoNaoPermitidaException;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.RegistroDuplicadoException;
import io.github.arthursilvagbs.bytebank.ByteBank.mappers.ClienteMapper;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.ClienteRepository;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {

   private final ClienteRepository repository;
   private final ContaRepository contaRepository;
   private final ClienteMapper mapper;

   @Transactional
   public Cliente salvarPessoaFisica(PessoaFisicaCreateDTO dto) {
      if (repository.findByCpf(dto.cpf()).isPresent() || repository.findByEmail(dto.email()).isPresent()) {
         throw new RegistroDuplicadoException("Cliente já cadastrado");
      }

      Cliente cliente = mapper.mapearParaPessoaFisica(dto);
      return repository.save(cliente);
   }

   @Transactional
   public Cliente salvarPessoaJuridica(PessoaJuridicaCreateDTO dto) {
      if (repository.findByCpf(dto.cnpj()).isPresent() || repository.findByEmail(dto.email()).isPresent()) {
         throw new RegistroDuplicadoException("Cliente já cadastrado");
      }

      Cliente cliente = mapper.mapearParaPessoaJuridica(dto);
      return repository.save(cliente);
   }

   @Transactional
   public void atualizar(String id, ClienteUpdateDTO dto) {
      UUID idCliente  = UUID.fromString(id);
      Cliente cliente = repository.findById(idCliente).orElseThrow(() -> new ClienteNaoEcontradoException("Cliente não encontrado"));

      cliente.setNome(dto.nome());
      cliente.setEmail(dto.email());
      cliente.setTelefone(dto.telefone());
      cliente.setEndereco(dto.endereco());
      repository.save(cliente);
   }

   @Transactional(readOnly = true)
   public Cliente obterPorId(String id) {
      UUID idCliente = UUID.fromString(id);
      return repository.findById(idCliente)
         .orElseThrow(() -> new ClienteNaoEcontradoException("Cliente não encontrado"));
   }

   @Transactional
   public void deletar(String id) {
      UUID idCliente = UUID.fromString(id);

      Cliente cliente = repository.findById(idCliente)
         .orElseThrow(() -> new ClienteNaoEcontradoException("Cliente não encontrado"));

      if (possuiConta(cliente)) {
         throw new OperacaoNaoPermitidaException("O cliente não pode ser deletado, pois ainda existem contas vinculadas a ele.");
      }
      repository.delete(cliente);
   }

   @Transactional(readOnly = true)
   public Cliente pesquisaPorNome(String nome) {
      return repository.findByNome(nome)
         .orElseThrow(() -> new ClienteNaoEcontradoException("Cliente não encontrado"));
   }

   @Transactional(readOnly = true)
   public List<Cliente> obterTodosOsClientes() {
      return repository.findAll();
   }

   @Transactional(readOnly = true)
   public boolean possuiConta(Cliente cliente) {
      return contaRepository.existsByCliente(cliente);
   }

}
