package io.github.arthursilvagbs.bytebank.ByteBank.repository;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente.PessoaFisicaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ClienteRespositoryTest {

   @Autowired
   ClienteRespository repository;

   @Autowired
   EntityManager em;

   @Test
   @DisplayName("Deve buscar com sucesso no banco de dados, um cliente pelo nome")
   void findByNomeSucess() {
      String nome = "Arthur";
      PessoaFisicaCreateDTO dto = new PessoaFisicaCreateDTO(nome, "arthur@email.com", "61912345678", "endereco, rua 10, casa 1", "12345678900");
      this.createCliente(dto);

      Optional<Cliente> clienteEcontrado = this.repository.findByNome(nome);

      assertThat(clienteEcontrado.isPresent()).isTrue();
   }

   @Test
   @DisplayName("Não deve conseguir buscar no banco de dados, um cliente pelo nome")
   void findByNomeErro() {
      String nome = "Arthur";

      Optional<Cliente> clienteEcontrado = this.repository.findByNome(nome);

      assertThat(clienteEcontrado.isEmpty()).isTrue();
   }

   private Cliente createCliente(PessoaFisicaCreateDTO dto){
      Cliente newCliente = new Cliente(dto);
      this.em.persist(newCliente);
      return newCliente;
   }
}