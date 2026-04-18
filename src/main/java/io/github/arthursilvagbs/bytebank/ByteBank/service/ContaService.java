package io.github.arthursilvagbs.bytebank.ByteBank.service;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.conta.ContaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.conta.ContaUpdateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.ClienteNaoEncontradoException;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.ContaNaoEncontradaException;
import io.github.arthursilvagbs.bytebank.ByteBank.mappers.ContaMapper;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.ClienteRepository;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContaService {

   private final ContaRepository repository;
   private final ClienteRepository clienteRepository;
   private final ContaMapper mapper;

   @Transactional
   public Conta salvar(ContaCreateDTO dto){
      Cliente cliente = clienteRepository.findById(dto.clienteId())
         .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado"));

      Conta conta = mapper.mapearParaConta(cliente);
      return repository.save(conta);
   }

   @Transactional
   public void atualizar(String id, ContaUpdateDTO dto) {
      UUID idConta = UUID.fromString(id);

      Conta conta = repository.findById(idConta)
            .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada"));

      conta.setSaldo(dto.saldo());

      repository.save(conta);
   }

   @Transactional(readOnly = true)
   public Conta obterPorID(String id) {
      UUID idConta = UUID.fromString(id);
      return repository.findById(idConta)
         .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada"));
   }

   @Transactional(readOnly = true)
   public Conta obterPorNumConta(Integer numeroConta) {
      return repository.findByNumeroConta(numeroConta)
         .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada"));
   }

   @Transactional(readOnly = true)
   public List<Conta> obterTodasAsContas() {
      return repository.findAll();
   }

   @Transactional(readOnly = true)
   public List<Conta> obterContasPorCliente(Cliente cliente) {
      return repository.findAllByCliente(cliente);
   }

   @Transactional
   public void deletar(String id) {
      UUID idConta = UUID.fromString(id);

      Conta conta = repository.findById(idConta)
         .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada"));

      repository.delete(conta);
   }

}
