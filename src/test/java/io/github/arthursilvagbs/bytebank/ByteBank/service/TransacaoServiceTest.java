package io.github.arthursilvagbs.bytebank.ByteBank.service;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.transacao.TransacaoCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.transacao.TransferenciaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.ContaNaoEncontradaException;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.SaldoInsuficienteException;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.ValorInvalidoException;
import io.github.arthursilvagbs.bytebank.ByteBank.mappers.TransacaoMapper;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Transacao;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.ContaRepository;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.TransacaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {

   @Mock
   private TransacaoRepository repository;

   @Mock
   private ContaRepository contaRepository;

   @Mock
   private TransacaoMapper mapper;

   @InjectMocks
   private TransacaoService service;

   @Test
   @DisplayName("Deve efetuar o deposito com sucesso")
   void depositoSuccess() {
      UUID contaId = UUID.randomUUID();
      Conta conta = new Conta();
      conta.setSaldo(new BigDecimal("500.00"));

      TransacaoCreateDTO dto = new TransacaoCreateDTO(new BigDecimal("200.00"), contaId);

      Transacao transacaoMapeada = new Transacao();

      when(contaRepository.findById(contaId)).thenReturn(Optional.of(conta));
      when(mapper.mapearParaTransacao(any(), any())).thenReturn(transacaoMapeada);
      when(repository.save(any())).thenReturn(transacaoMapeada);

      Transacao resultado = service.deposito(dto);

      assertNotNull(resultado);
      assertEquals(new BigDecimal("700.00"), conta.getSaldo());

      verify(contaRepository).save(conta);
      verify(repository).save(transacaoMapeada);
   }

   @Test
   @DisplayName("Deve lançar uma exception de conta não encontrada")
   void depositoContaNaoEncontrada() {
      UUID contaId = UUID.randomUUID();
      TransacaoCreateDTO dto = new TransacaoCreateDTO(new BigDecimal("200.00"), contaId);

      when(contaRepository.findById(contaId)).thenReturn(Optional.empty());

      assertThrows(ContaNaoEncontradaException.class, () -> service.deposito(dto));
   }

   @Test
   @DisplayName("Deve lançar uma exception pra um deposito com valor negativo")
   void depositoValorNegativo() {
      UUID contaId = UUID.randomUUID();
      Conta conta = new Conta();
      conta.setSaldo(new BigDecimal("500.00"));
      TransacaoCreateDTO dto = new TransacaoCreateDTO(new BigDecimal("-100.00"), contaId);

      when(contaRepository.findById(contaId)).thenReturn(Optional.of(conta));

      assertThrows(ValorInvalidoException.class, () -> service.deposito(dto));
   }

   @Test
   @DisplayName("Deve lançar uma exception pra um deposito com valor igual a 0")
   void depositoValorZero() {
      UUID contaId = UUID.randomUUID();
      Conta conta = new Conta();
      conta.setSaldo(new BigDecimal("500.00"));
      TransacaoCreateDTO dto = new TransacaoCreateDTO(new BigDecimal("0"), contaId);

      when(contaRepository.findById(contaId)).thenReturn(Optional.of(conta));

      assertThrows(ValorInvalidoException.class, () -> service.deposito(dto));
   }

   @Test
   @DisplayName("Deve efetuar o saque com sucesso")
   void saqueSuccess() {
      UUID contaId = UUID.randomUUID();
      Conta conta = new Conta();
      conta.setSaldo(new BigDecimal("500.00"));

      TransacaoCreateDTO dto = new TransacaoCreateDTO(new BigDecimal("200.00"), contaId);

      Transacao transacaoMapeada = new Transacao();

      when(contaRepository.findById(contaId)).thenReturn(Optional.of(conta));
      when(mapper.mapearParaTransacao(any(), any())).thenReturn(transacaoMapeada);
      when(repository.save(any())).thenReturn(transacaoMapeada);

      Transacao resultado = service.saque(dto);

      assertNotNull(resultado);
      assertEquals(new BigDecimal("300.00"), conta.getSaldo());

      verify(contaRepository).save(conta);
      verify(repository).save(transacaoMapeada);
   }

   @Test
   @DisplayName("Deve lançar uma exception de conta não encontrada")
   void saqueContaNaoEncontrada() {
      UUID contaId = UUID.randomUUID();
      TransacaoCreateDTO dto = new TransacaoCreateDTO(new BigDecimal("200.00"), contaId);
      when(contaRepository.findById(contaId)).thenReturn(Optional.empty());
      assertThrows(ContaNaoEncontradaException.class, () -> service.saque(dto));
   }

   @Test
   @DisplayName("Deve lançar uma exception para um deposito com valor negativo")
   void saqueValorNegativo() {
      UUID contaId = UUID.randomUUID();
      Conta conta = new Conta();
      conta.setSaldo(new BigDecimal("500.00"));
      TransacaoCreateDTO dto = new TransacaoCreateDTO(new BigDecimal("-200.00"), contaId);

      when(contaRepository.findById(contaId)).thenReturn(Optional.of(conta));

      assertThrows(ValorInvalidoException.class, () -> service.saque(dto));
   }

   @Test
   @DisplayName("Deve lançar uma exception para um deposito com valor igual a 0")
   void saqueValorZero() {
      UUID contaId = UUID.randomUUID();
      Conta conta = new Conta();
      conta.setSaldo(new BigDecimal("500.00"));
      TransacaoCreateDTO dto = new TransacaoCreateDTO(new BigDecimal("0.00"), contaId);

      when(contaRepository.findById(contaId)).thenReturn(Optional.of(conta));

      assertThrows(ValorInvalidoException.class, () -> service.saque(dto));
   }

   @Test
   @DisplayName("Deve lançar uma exception pra quando o saldo da conta for insuficiente para o saque")
   void saqueSaldoInsuficiente() {
      UUID contaId = UUID.randomUUID();
      Conta conta = new Conta();
      conta.setSaldo(new BigDecimal("200.00"));
      TransacaoCreateDTO dto = new TransacaoCreateDTO(new BigDecimal("500.00"), contaId);

      when(contaRepository.findById(contaId)).thenReturn(Optional.of(conta));

      assertThrows(SaldoInsuficienteException.class, () -> service.saque(dto));
   }

   @Test
   @DisplayName("Deve efetuar a transferencia com sucesso")
   void transferenciaSuccess() {
      UUID contaOrigemId = UUID.randomUUID();
      Conta contaOrigem = new Conta();
      contaOrigem.setSaldo(new BigDecimal("500.00"));

      UUID contaDestinoId = UUID.randomUUID();
      Conta contaDestino = new Conta();
      contaDestino.setSaldo(new BigDecimal("0.00"));

      TransferenciaCreateDTO dto = new TransferenciaCreateDTO(new BigDecimal("250.00"), contaOrigemId, contaDestinoId);

      Transacao transacaoMapeada = new Transacao();

      when(contaRepository.findById(contaOrigemId)).thenReturn(Optional.of(contaOrigem));
      when(contaRepository.findById(contaDestinoId)).thenReturn(Optional.of(contaDestino));
      when(mapper.mapearParaTransferencia(any(), any(), any())).thenReturn(transacaoMapeada);
      when(repository.save(any())).thenReturn(transacaoMapeada);

      Transacao resultado = service.transferencia(dto);

      assertNotNull(resultado);
      assertEquals(new BigDecimal("250.00"), contaOrigem.getSaldo());
      assertEquals(new BigDecimal("250.00"), contaDestino.getSaldo());

      verify(contaRepository).save(contaOrigem);
      verify(contaRepository).save(contaDestino);
      verify(repository).save(resultado);
   }

   @Test
   @DisplayName("Deve lançar uma exception de conta não encontrada, em relação a conta origem")
   void tranferenciaContaOrigemNaoEncontrada() {
      UUID contaOrigemId = UUID.randomUUID();
      UUID contaDestinoId = UUID.randomUUID();
      TransferenciaCreateDTO dto = new TransferenciaCreateDTO(new BigDecimal("200.00"), contaOrigemId, contaDestinoId);

      when(contaRepository.findById(contaOrigemId)).thenReturn(Optional.empty());

      assertThrows(ContaNaoEncontradaException.class, () -> service.transferencia(dto));
   }

   @Test
   @DisplayName("Deve lançar uma exception de conta não encontrada, em relação a conta destino")
   void tranferenciaContaDestinoNaoEncontrada() {
      UUID contaOrigemId = UUID.randomUUID();
      Conta contaOrigem = new Conta();
      contaOrigem.setSaldo(new BigDecimal("500.00"));

      UUID contaDestinoId = UUID.randomUUID();
      TransferenciaCreateDTO dto = new TransferenciaCreateDTO(new BigDecimal("200.00"), contaOrigemId, contaDestinoId);

      when(contaRepository.findById(contaOrigemId)).thenReturn(Optional.of(contaOrigem));
      when(contaRepository.findById(contaDestinoId)).thenReturn(Optional.empty());

      assertThrows(ContaNaoEncontradaException.class, () -> service.transferencia(dto));
   }

   @Test
   @DisplayName("Deve lançar uma exception para uma tranferencia com valor negativo")
   void transferenciaValorNegativo() {
      UUID contaOrigemId = UUID.randomUUID();
      Conta contaOrigem = new Conta();
      contaOrigem.setSaldo(new BigDecimal("500.00"));

      UUID contaDestinoId = UUID.randomUUID();
      Conta contaDestino = new Conta();
      contaDestino.setSaldo(new BigDecimal("100.00"));

      TransferenciaCreateDTO dto = new TransferenciaCreateDTO(new BigDecimal("-200.00"), contaOrigemId, contaDestinoId);

      assertThrows(ValorInvalidoException.class, () -> service.transferencia(dto));
   }

   @Test
   @DisplayName("Deve lançar uma exception para uma tranferencia com valor igual a 0")
   void transferenciaValorZero() {
      UUID contaOrigemId = UUID.randomUUID();
      Conta contaOrigem = new Conta();
      contaOrigem.setSaldo(new BigDecimal("500.00"));

      UUID contaDestinoId = UUID.randomUUID();
      Conta contaDestino = new Conta();
      contaDestino.setSaldo(new BigDecimal("100.00"));

      TransferenciaCreateDTO dto = new TransferenciaCreateDTO(new BigDecimal("0.00"), contaOrigemId, contaDestinoId);

      assertThrows(ValorInvalidoException.class, () -> service.transferencia(dto));
   }

   @Test
   @DisplayName("Deve lançar uma exceptio pra saldo insuficiente")
   void transferenciaSaldoInsuficiente() {
      UUID contaOrigemId = UUID.randomUUID();
      Conta contaOrigem = new Conta();
      contaOrigem.setSaldo(new BigDecimal("100.00"));

      UUID contaDestinoId = UUID.randomUUID();

      TransferenciaCreateDTO dto = new TransferenciaCreateDTO(new BigDecimal("200.00"), contaOrigemId, contaDestinoId);

      when(contaRepository.findById(contaOrigemId)).thenReturn(Optional.of(contaOrigem));

      assertThrows(SaldoInsuficienteException.class, () -> service.transferencia(dto));
   }
}