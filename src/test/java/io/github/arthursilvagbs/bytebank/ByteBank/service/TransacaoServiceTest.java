package io.github.arthursilvagbs.bytebank.ByteBank.service;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.transacao.TransacaoCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.ContaNaoEncontradaException;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.ValorInvalidoException;
import io.github.arthursilvagbs.bytebank.ByteBank.mappers.TransacaoMapper;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Transacao;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.ContaRpository;
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
   private ContaRpository contaRepository;

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
   @DisplayName("Deve lançar uma exceção de conta não encontrada")
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
   void saque() {
      UUID contaId = UUID.randomUUID();
      Conta conta = new Conta();
      conta.setSaldo(new BigDecimal("500"));

      TransacaoCreateDTO dto = new TransacaoCreateDTO(new BigDecimal("100"), contaId);
      Transacao transacaoMapeada = new Transacao();

      Transacao resultado = service.saque(dto);

      when(contaRepository.findById(contaId)).thenReturn(Optional.of(conta));
      when(mapper.mapearParaTransacao(any(), any())).thenReturn(transacaoMapeada);
      when(repository.save(any())).thenReturn(transacaoMapeada);

      assertNotNull(resultado);
      assertEquals(new BigDecimal("600"), conta.getSaldo());

      verify(contaRepository.save(conta));
      verify(repository.save(resultado));
   }



   @Test
   void transferencia() {
   }

   @Test
   void buscarTransacaoPorId() {
   }

   @Test
   void buscarMovimentacoesPorConta() {
   }
}