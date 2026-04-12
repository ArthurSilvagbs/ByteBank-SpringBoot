package io.github.arthursilvagbs.bytebank.ByteBank.service;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.PessoaFisicaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.PessoaJuridicaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.OperacaoNaoPermitidaException;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.RegistroDuplicadoException;
import io.github.arthursilvagbs.bytebank.ByteBank.mappers.ClienteMapper;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
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

   private final ClienteRepository repository;
   private final ContaRepository contaRepository;
   private final ClienteMapper mapper;

   public Cliente salvarPessoaFisica(PessoaFisicaCreateDTO dto) {
      if (repository.findByCpf(dto.cpf()).isPresent() || repository.findByEmail(dto.email()).isPresent()) {
         throw new RegistroDuplicadoException("Cliente já cadastrado");
      }

      Cliente cliente = mapper.mapearParaPessoaFisica(dto);
      return repository.save(cliente);
   }

   public Cliente salvarPessoaJuridica(PessoaJuridicaCreateDTO dto) {
      if (repository.findByCpf(dto.cnpj()).isPresent() || repository.findByEmail(dto.email()).isPresent()) {
         throw new RegistroDuplicadoException("Cliente já cadastrado");
      }

      Cliente cliente = mapper.mapearParaPessoaJuridica(dto);
      return repository.save(cliente);
   }

   public void atualizar(Cliente cliente) {
      if (cliente.getId() == null) {
         throw new IllegalArgumentException("Para atualizar um cliente, é ncessário que ele esteja cadastrado.");
      }
      repository.save(cliente);
   }

   public Optional<Cliente> obterPorId(UUID id) {
      return repository.findById(id);
   }

   public void deletar(Cliente cliente) {
      if (possuiConta(cliente)) {
         throw new OperacaoNaoPermitidaException("O cliente não pode ser deletado, pois ainda existem contas vinculadas a ele.");
      }
      repository.delete(cliente);
   }

   public Optional<Cliente> pesquisaPorNome(String nome) {
      return repository.findByNome(nome);
   }

   public Optional<Cliente> pesquisaPorId(UUID id) {
      return repository.findById(id);
   }

   public List<Cliente> obterTodosOsClientes() {
      return repository.findAll();
   }

   public boolean possuiConta(Cliente cliente) {
      return contaRepository.existsByCliente(cliente);
   }

}
