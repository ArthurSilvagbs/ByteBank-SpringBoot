package io.github.arthursilvagbs.bytebank.ByteBank.service;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.conta.ContaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.conta.ContaUpdateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.ClienteNaoEcontradoException;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.ContaNaoEncontradaException;
import io.github.arthursilvagbs.bytebank.ByteBank.mappers.ContaMapper;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.ClienteRepository;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.ContaRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

   @Mock
   private ContaRepository repository;

   @Mock
   private ClienteRepository clienteRepository;

   @Mock
   private ContaMapper mapper;

   @InjectMocks
   private ContaService service;

   // ===================== salvar =====================

   @Test
   @DisplayName("Deve criar uma conta com sucesso quando o cliente existir")
   void salvarContaComSucesso() {
      UUID clienteId = UUID.randomUUID();
      ContaCreateDTO dto = new ContaCreateDTO(clienteId);

      Cliente cliente = new Cliente();
      Conta contaMapeada = new Conta(cliente);

      when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
      when(mapper.mapearParaConta(cliente)).thenReturn(contaMapeada);
      when(repository.save(contaMapeada)).thenReturn(contaMapeada);

      Conta resultado = service.salvar(dto);

      assertNotNull(resultado);
      verify(clienteRepository).findById(clienteId);
      verify(mapper).mapearParaConta(cliente);
      verify(repository).save(contaMapeada);
   }

   @Test
   @DisplayName("Deve lançar ClienteNaoEcontradoException ao tentar criar conta com cliente inexistente")
   void salvarContaClienteNaoEncontrado() {
      UUID clienteId = UUID.randomUUID();
      ContaCreateDTO dto = new ContaCreateDTO(clienteId);

      when(clienteRepository.findById(clienteId)).thenReturn(Optional.empty());

      assertThrows(ClienteNaoEcontradoException.class, () -> service.salvar(dto));

      verify(clienteRepository).findById(clienteId);
      verify(repository, never()).save(any());
   }

   // ===================== atualizar =====================

   @Test
   @DisplayName("Deve atualizar o saldo da conta com sucesso")
   void atualizarSaldoComSucesso() {
      UUID contaId = UUID.randomUUID();
      ContaUpdateDTO dto = new ContaUpdateDTO(new BigDecimal("1500.00"));

      Conta conta = new Conta();
      conta.setSaldo(new BigDecimal("1000.00"));

      when(repository.findById(contaId)).thenReturn(Optional.of(conta));

      service.atualizar(contaId.toString(), dto);

      assertEquals(new BigDecimal("1500.00"), conta.getSaldo());
      verify(repository).save(conta);
   }

   @Test
   @DisplayName("Deve lançar ContaNaoEncontradaException ao tentar atualizar conta inexistente")
   void atualizarContaNaoEncontrada() {
      UUID contaId = UUID.randomUUID();
      ContaUpdateDTO dto = new ContaUpdateDTO(new BigDecimal("1500.00"));

      when(repository.findById(contaId)).thenReturn(Optional.empty());

      assertThrows(ContaNaoEncontradaException.class, () -> service.atualizar(contaId.toString(), dto));

      verify(repository, never()).save(any());
   }

   // ===================== deletar =====================

   // TODO: Deve deletar a conta com sucesso quando ela existir
   // - mockar repository.findById(id) retornando Optional.of(conta)
   // - chamar service.deletar(id.toString())
   // - verify(repository).delete(conta)



   // TODO: Deve lançar ContaNaoEncontradaException ao tentar deletar conta inexistente
   // - mockar repository.findById(id) retornando Optional.empty()
   // - assertThrows(ContaNaoEncontradaException.class, () -> service.deletar(id.toString()))
   // - verify(repository, never()).delete(any())

   // ===================== obterPorID =====================

   // TODO: Deve retornar Optional com a conta quando o ID existir
   // - mockar repository.findById(id) retornando Optional.of(conta)
   // - chamar service.obterPorID(id)
   // - assertTrue(resultado.isPresent())
   // - assertEquals(conta, resultado.get())

   // TODO: Deve retornar Optional vazio quando o ID não existir
   // - mockar repository.findById(id) retornando Optional.empty()
   // - chamar service.obterPorID(id)
   // - assertTrue(resultado.isEmpty())
}
