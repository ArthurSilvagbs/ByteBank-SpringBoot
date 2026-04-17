package io.github.arthursilvagbs.bytebank.ByteBank.service;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.transacao.TransacaoCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.transacao.TransferenciaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.ContaNaoEncontradaException;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.RecursoNaoEncontradoException;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.SaldoInsuficienteException;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.ValorInvalidoException;
import io.github.arthursilvagbs.bytebank.ByteBank.mappers.TransacaoMapper;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Transacao;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.ContaRepository;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransacaoService {

   private final TransacaoRepository repository;
   private final ContaRepository contaRepository;
   private final TransacaoMapper mapper;

   @Transactional
   public Transacao deposito(TransacaoCreateDTO dto) {
      Conta contaOrigem = contaRepository.findById(dto.IdContaOrigem())
         .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada"));

      if (dto.valor().compareTo(BigDecimal.ZERO) <= 0) {
         throw new ValorInvalidoException("Valor negativo não permitido");
      }

      BigDecimal saldoAtual = contaOrigem.getSaldo().add(dto.valor());
      contaOrigem.setSaldo(saldoAtual);
      Transacao transacao = mapper.mapearParaTransacao(dto.valor(), contaOrigem);
      contaRepository.save(contaOrigem);
      return repository.save(transacao);
   }

   @Transactional
   public Transacao saque(TransacaoCreateDTO dto) {
      Conta contaOrigem = contaRepository.findById(dto.IdContaOrigem())
         .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada"));

      if (dto.valor().compareTo(BigDecimal.ZERO) <= 0) {
         throw new ValorInvalidoException("Valor negativo não permitido");
      }

      if (contaOrigem.getSaldo().compareTo(dto.valor()) <= 0) {
         throw new SaldoInsuficienteException("Saldo insuficiente");
      }

      BigDecimal saldoAtual = contaOrigem.getSaldo().subtract(dto.valor());
      contaOrigem.setSaldo(saldoAtual);
      Transacao transacao = mapper.mapearParaTransacao(dto.valor(), contaOrigem);
      contaRepository.save(contaOrigem);
      return repository.save(transacao);
   }

   @Transactional
   public Transacao transferencia(TransferenciaCreateDTO dto) {
      if (dto.valor().compareTo(BigDecimal.ZERO) <= 0) {
         throw new ValorInvalidoException("Valor negativo não é permitido");
      }

      Conta contaOrigem = contaRepository.findById(dto.IdContaOrigem())
         .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada"));

      if (contaOrigem.getSaldo().compareTo(dto.valor()) <= 0) {
         throw new SaldoInsuficienteException("Saldo insuficiente");
      }

      Conta contaDestino = contaRepository.findById(dto.IdContaDestino())
         .orElseThrow(() -> new ContaNaoEncontradaException("Conta não encontrada"));

      BigDecimal saldoContaOrigem = contaOrigem.getSaldo().subtract(dto.valor());
      contaOrigem.setSaldo(saldoContaOrigem);
      contaRepository.save(contaOrigem);

      BigDecimal saldoContaDestino = contaDestino.getSaldo().add(dto.valor());
      contaDestino.setSaldo(saldoContaDestino);
      contaRepository.save(contaDestino);

      Transacao transacao = mapper.mapearParaTransferencia(dto.valor(), contaOrigem, contaDestino);

      return repository.save(transacao);
   }

   @Transactional(readOnly = true)
   public Transacao buscarTransacaoPorId(String id) {
      UUID idTransacao = UUID.fromString(id);
      return repository.findById(idTransacao)
         .orElseThrow(() -> new RecursoNaoEncontradoException("Transação não encontrada"));
   }

   @Transactional(readOnly = true)
   public List<Transacao> buscarMovimentacoesPorConta(Conta conta) {
      return repository.findAllByContaOrigem(conta);
   }
}
