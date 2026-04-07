package io.github.arthursilvagbs.bytebank.ByteBank.service;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.transacao.TransacaoCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.transacao.TransferenciaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.SaldoInsuficienteException;
import io.github.arthursilvagbs.bytebank.ByteBank.mappers.TransacaoMapper;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Transacao;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.ContaRpository;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.TransacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransacaoService {

   private final TransacaoRepository repository;
   private final ContaRpository contaRepository;
   private final TransacaoMapper mapper;

   public Transacao salvar(Transacao transacao) {
      return repository.save(transacao);
   }

   public Transacao deposito(TransacaoCreateDTO dto) {
      Conta contaOrigem = contaRepository.findById(dto.IdContaOrigem())
         .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

      if (dto.valor().compareTo(BigDecimal.ZERO) < 0) {
         throw new RuntimeException("Valor negativo não permitido");
      }

      if (contaOrigem.getSaldo().compareTo(dto.valor()) < 0) {
         throw new SaldoInsuficienteException("Saldo insuficiente");
      }

      BigDecimal saldoAtual = contaOrigem.getSaldo().add(dto.valor());
      contaOrigem.setSaldo(saldoAtual);
      Transacao transacao = mapper.mapearParaTransacao(dto.valor(), contaOrigem);
      contaRepository.save(contaOrigem);
      return repository.save(transacao);
   }

   public Transacao saque(TransacaoCreateDTO dto) {
      Conta contaOrigem = contaRepository.findById(dto.IdContaOrigem())
         .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

      if (dto.valor().compareTo(BigDecimal.ZERO) < 0) {
         throw new RuntimeException("Valor negativo não permitido");
      }

      if (contaOrigem.getSaldo().compareTo(dto.valor()) < 0) {
         throw new SaldoInsuficienteException("Saldo insuficiente");
      }

      BigDecimal saldoAtual = contaOrigem.getSaldo().subtract(dto.valor());
      contaOrigem.setSaldo(saldoAtual);
      Transacao transacao = mapper.mapearParaTransacao(dto.valor(), contaOrigem);
      contaRepository.save(contaOrigem);
      return repository.save(transacao);
   }

   public Transacao transferencia(TransferenciaCreateDTO dto) {
      if (dto.valor().compareTo(BigDecimal.ZERO) < 0) {
         throw new RuntimeException("Valor negativo não é permitido");
      }

      Conta contaOrigem = contaRepository.findById(dto.IdContaOrigem())
         .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

      if (contaOrigem.getSaldo().compareTo(dto.valor()) < 0) {
         throw new SaldoInsuficienteException("Saldo insuficiente");
      }

      Conta contaDestino = contaRepository.findById(dto.IdContaDestino())
         .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

      BigDecimal saldoContaOrigem = contaOrigem.getSaldo().subtract(dto.valor());
      contaOrigem.setSaldo(saldoContaOrigem);
      contaRepository.save(contaOrigem);

      BigDecimal saldoContaDestino = contaDestino.getSaldo().add(dto.valor());
      contaDestino.setSaldo(saldoContaDestino);
      contaRepository.save(contaDestino);

      Transacao transacao = mapper.mapearParaTransferencia(dto.valor(), contaOrigem, contaDestino);

      return repository.save(transacao);
   }

   public Optional<Transacao> buscarTransacaoPorId(UUID id) {
      return repository.findById(id);
   }

   public List<Transacao> buscarMovimentacoesPorConta(Conta conta) {
      return repository.findAllByContaOrigem(conta);
   }
}
