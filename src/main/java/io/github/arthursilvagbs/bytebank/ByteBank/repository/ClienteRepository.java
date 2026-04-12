package io.github.arthursilvagbs.bytebank.ByteBank.repository;

import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findByNome(String nome);

    Optional<Cliente> findByEmail(String email);

    @Query("SELECT p FROM PessoaFisica p WHERE p.cpf = :cpf")
    Optional<Cliente> findByCpf(@Param("cpf") String cpf);

    @Query("SELECT p FROM PessoaJuridica p WHERE p.cnpj = :cnpj")
    Optional<Cliente> findByCnpj(@Param("cnpj") String cnpj);

}
