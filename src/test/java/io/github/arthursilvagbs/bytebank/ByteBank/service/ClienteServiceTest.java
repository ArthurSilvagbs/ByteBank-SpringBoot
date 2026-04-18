package io.github.arthursilvagbs.bytebank.ByteBank.service;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.ClienteUpdateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.PessoaFisicaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.PessoaJuridicaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.ClienteNaoEncontradoException;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.OperacaoNaoPermitidaException;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.RegistroDuplicadoException;
import io.github.arthursilvagbs.bytebank.ByteBank.mappers.ClienteMapper;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.model.PessoaFisica;
import io.github.arthursilvagbs.bytebank.ByteBank.model.PessoaJuridica;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.ClienteRepository;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.ContaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

   @Mock
   private ClienteRepository repository;

   @Mock
   private ContaRepository contaRepository;

   @Mock
   private ClienteMapper mapper;

   @InjectMocks
   private ClienteService service;

   // ===================== salvarPessoaFisica =====================

   @Test
   @DisplayName("Deve salvar um cliente pessoa física com sucesso")
   void salvarPessoaFisicaComSucesso() {
      PessoaFisicaCreateDTO dto = new PessoaFisicaCreateDTO("nome", "teste@email.com", "12345678", "endereço", "12345678900");
      Cliente clienteMapeado = new PessoaFisica();

      when(repository.findByCpf(dto.cpf())).thenReturn(Optional.empty());
      when(repository.findByEmail(dto.email())).thenReturn(Optional.empty());
      when(mapper.mapearParaPessoaFisica(dto)).thenReturn(clienteMapeado);
      when(repository.save(clienteMapeado)).thenReturn(clienteMapeado);

      Cliente resultado = service.salvarPessoaFisica(dto);

      assertNotNull(resultado);
      verify(repository).save(clienteMapeado);
   }

   @Test
   @DisplayName("Deve lançar RegistroDuplicadoException quando CPF já estiver cadastrado")
   void salvarPessoaFisicaCpfDuplicado() {
      PessoaFisicaCreateDTO dto = new PessoaFisicaCreateDTO("nome", "teste@email.com", "12345678", "endereço", "12345678900");

      when(repository.findByCpf(dto.cpf())).thenReturn(Optional.of(new PessoaFisica()));

      assertThrows(RegistroDuplicadoException.class, () -> service.salvarPessoaFisica(dto));

      verify(repository, never()).save(any());
   }

   @Test
   @DisplayName("Deve lançar RegistroDuplicadoException quando email já estiver cadastrado")
   void salvarPessoaFisicaEmailDuplicado() {
      PessoaFisicaCreateDTO dto = new PessoaFisicaCreateDTO("nome", "teste@email.com", "12345678", "endereço", "12345678900");

      when(repository.findByCpf(dto.cpf())).thenReturn(Optional.empty());
      when(repository.findByEmail(dto.email())).thenReturn(Optional.of(new PessoaFisica()));

      assertThrows(RegistroDuplicadoException.class, () -> service.salvarPessoaFisica(dto));

      verify(repository, never()).save(any());
   }

   // ===================== salvarPessoaJuridica =====================

   @Test
   @DisplayName("Deve salvar um cliente pessoa jurídica com sucesso")
   void salvarPessoaJuridicaComSucesso() {
      PessoaJuridicaCreateDTO dto = new PessoaJuridicaCreateDTO("nome", "teste@email.com", "12345678", "endereço", "12345678900011");
      Cliente clienteMapeado = new PessoaJuridica();

      when(repository.findByCpf(dto.cnpj())).thenReturn(Optional.empty());
      when(repository.findByEmail(dto.email())).thenReturn(Optional.empty());
      when(mapper.mapearParaPessoaJuridica(dto)).thenReturn(clienteMapeado);
      when(repository.save(clienteMapeado)).thenReturn(clienteMapeado);

      Cliente resultado = service.salvarPessoaJuridica(dto);

      assertNotNull(resultado);
      verify(repository).save(clienteMapeado);
   }

   @Test
   @DisplayName("Deve lançar RegistroDuplicadoException quando CNPJ já estiver cadastrado")
   void salvarPessoaJuridicaCnpjDuplicado() {
      PessoaJuridicaCreateDTO dto = new PessoaJuridicaCreateDTO("nome", "teste@email.com", "12345678", "endereço", "12345678900011");

      when(repository.findByCpf(dto.cnpj())).thenReturn(Optional.of(new PessoaJuridica()));

      assertThrows(RegistroDuplicadoException.class, () -> service.salvarPessoaJuridica(dto));

      verify(repository, never()).save(any());
   }

   @Test
   @DisplayName("Deve lançar RegistroDuplicadoException quando email já estiver cadastrado para pessoa jurídica")
   void salvarPessoaJuridicaEmailDuplicado() {
      PessoaJuridicaCreateDTO dto = new PessoaJuridicaCreateDTO("nome", "teste@email.com", "12345678", "endereço", "12345678900011");

      when(repository.findByCpf(dto.cnpj())).thenReturn(Optional.empty());
      when(repository.findByEmail(dto.email())).thenReturn(Optional.of(new PessoaJuridica()));

      assertThrows(RegistroDuplicadoException.class, () -> service.salvarPessoaJuridica(dto));

      verify(repository, never()).save(any());
   }

   // ===================== atualizar =====================

   @Test
   @DisplayName("Deve atualizar os dados do cliente com sucesso")
   void atualizarClienteComSucesso() {
      UUID id = UUID.randomUUID();
      ClienteUpdateDTO dto = new ClienteUpdateDTO("Novo Nome", "novo@email.com", "99999999", "Nova Rua");
      Cliente cliente = new Cliente();

      when(repository.findById(id)).thenReturn(Optional.of(cliente));

      service.atualizar(id.toString(), dto);

      assertEquals("Novo Nome", cliente.getNome());
      assertEquals("novo@email.com", cliente.getEmail());
      assertEquals("99999999", cliente.getTelefone());
      assertEquals("Nova Rua", cliente.getEndereco());
      verify(repository).save(cliente);
   }

   @Test
   @DisplayName("Deve lançar ClienteNaoEcontradoException ao tentar atualizar cliente inexistente")
   void atualizarClienteNaoEncontrado() {
      UUID id = UUID.randomUUID();
      ClienteUpdateDTO dto = new ClienteUpdateDTO("Nome", "email@email.com", "99999999", "Rua");

      when(repository.findById(id)).thenReturn(Optional.empty());

      assertThrows(ClienteNaoEncontradoException.class, () -> service.atualizar(id.toString(), dto));

      verify(repository, never()).save(any());
   }

   // ===================== deletar =====================

   @Test
   @DisplayName("Deve deletar o cliente com sucesso quando não houver contas vinculadas")
   void deletarClienteComSucesso() {
      UUID id = UUID.randomUUID();
      Cliente cliente = new Cliente();

      when(repository.findById(id)).thenReturn(Optional.of(cliente));
      when(contaRepository.existsByCliente(cliente)).thenReturn(false);

      service.deletar(id.toString());

      verify(repository).delete(cliente);
   }

   @Test
   @DisplayName("Deve lançar OperacaoNaoPermitidaException ao tentar deletar cliente com contas vinculadas")
   void deletarClienteComContaVinculada() {
      UUID id = UUID.randomUUID();
      Cliente cliente = new Cliente();

      when(repository.findById(id)).thenReturn(Optional.of(cliente));
      when(contaRepository.existsByCliente(cliente)).thenReturn(true);

      assertThrows(OperacaoNaoPermitidaException.class, () -> service.deletar(id.toString()));

      verify(repository, never()).delete(any());
   }

   @Test
   @DisplayName("Deve lançar ClienteNaoEcontradoException ao tentar deletar cliente inexistente")
   void deletarClienteNaoEncontrado() {
      UUID id = UUID.randomUUID();

      when(repository.findById(id)).thenReturn(Optional.empty());

      assertThrows(ClienteNaoEncontradoException.class, () -> service.deletar(id.toString()));

      verify(repository, never()).delete(any());
   }

   // ===================== obterPorId =====================

   @Test
   @DisplayName("Deve retornar o cliente quando o ID existir")
   void obterPorIdComSucesso() {
      UUID id = UUID.randomUUID();
      Cliente cliente = new Cliente();

      when(repository.findById(id)).thenReturn(Optional.of(cliente));

      Cliente resultado = service.obterPorId(id.toString());

      assertNotNull(resultado);
      assertEquals(cliente, resultado);
   }

   @Test
   @DisplayName("Deve lançar ClienteNaoEcontradoException quando o ID não existir")
   void obterPorIdNaoEncontrado() {
      UUID id = UUID.randomUUID();

      when(repository.findById(id)).thenReturn(Optional.empty());

      assertThrows(ClienteNaoEncontradoException.class, () -> service.obterPorId(id.toString()));
   }

   // ===================== pesquisaPorNome =====================

   @Test
   @DisplayName("Deve retornar o cliente quando o nome existir")
   void pesquisaPorNomeComSucesso() {
      Cliente cliente = new Cliente();

      when(repository.findByNome("João")).thenReturn(Optional.of(cliente));

      Cliente resultado = service.pesquisaPorNome("João");

      assertNotNull(resultado);
      assertEquals(cliente, resultado);
   }

   @Test
   @DisplayName("Deve lançar ClienteNaoEcontradoException quando o nome não existir")
   void pesquisaPorNomeNaoEncontrado() {
      when(repository.findByNome("Inexistente")).thenReturn(Optional.empty());

      assertThrows(ClienteNaoEncontradoException.class, () -> service.pesquisaPorNome("Inexistente"));
   }

   // ===================== obterTodosOsClientes =====================

   @Test
   @DisplayName("Deve retornar lista com todos os clientes")
   void obterTodosOsClientesComSucesso() {
      List<Cliente> clientes = List.of(new PessoaFisica(), new PessoaJuridica());

      when(repository.findAll()).thenReturn(clientes);

      List<Cliente> resultado = service.obterTodosOsClientes();

      assertEquals(2, resultado.size());
      verify(repository).findAll();
   }

   // ===================== possuiConta =====================

   @Test
   @DisplayName("Deve retornar true quando o cliente possuir conta vinculada")
   void possuiContaRetornaTrue() {
      Cliente cliente = new Cliente();

      when(contaRepository.existsByCliente(cliente)).thenReturn(true);

      assertTrue(service.possuiConta(cliente));
   }

   @Test
   @DisplayName("Deve retornar false quando o cliente não possuir conta vinculada")
   void possuiContaRetornaFalse() {
      Cliente cliente = new Cliente();

      when(contaRepository.existsByCliente(cliente)).thenReturn(false);

      assertFalse(service.possuiConta(cliente));
   }
}
