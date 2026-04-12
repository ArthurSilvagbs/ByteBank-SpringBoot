package io.github.arthursilvagbs.bytebank.ByteBank.service;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.PessoaFisicaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.PessoaJuridicaCreateDTO;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

   @Test
   @DisplayName("Deve salvar um cliente pessoa física com sucesso")
   void salvarPessoaFisicaSuccess() {
      PessoaFisicaCreateDTO dto = new PessoaFisicaCreateDTO("nome", "teste@email.com", "12345678", "endereço", "12345678900");

      Cliente clienteMapeado = new PessoaFisica();

      when(mapper.mapearParaPessoaFisica(dto)).thenReturn(clienteMapeado);
      when(repository.save(clienteMapeado)).thenReturn(clienteMapeado);

      Cliente resultado = service.salvarPessoaFisica(dto);

      assertNotNull(resultado);

      verify(repository).save(clienteMapeado);
   }

   @Test
   @DisplayName("Deve salvar um cliente pessoa juridica com sucesso")
   void salvarPessoaJuridicaSucesses() {
      PessoaJuridicaCreateDTO dto = new PessoaJuridicaCreateDTO("nome", "teste@email.com", "12345678", "endereço", "12345678900011");

      Cliente clienteMapeado = new PessoaJuridica();

      when(mapper.mapearParaPessoaJuridica(dto)).thenReturn(clienteMapeado);
      when(repository.save(clienteMapeado)).thenReturn(clienteMapeado);

      Cliente resultado = service.salvarPessoaJuridica(dto);

      assertNotNull(resultado);

      verify(repository).save(clienteMapeado);
   }

   @Test
   @DisplayName("Deve aprensentar uma badRequest quando os dados forem inseridos erroniamente")
   void salvarError() {

   }

   @Test
   void atualizar() {
   }

   @Test
   void obterPorId() {
   }

   @Test
   void deletar() {
   }

   @Test
   void pesquisaPorNome() {
   }

   @Test
   void pesquisaPorId() {
   }

   @Test
   void obterTodosOsClientes() {
   }

   @Test
   void possuiConta() {
   }
}