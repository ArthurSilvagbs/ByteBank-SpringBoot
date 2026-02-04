package io.github.arthursilvagbs.bytebank.ByteBank.repository;

import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRespository extends JpaRepository<Cliente, UUID> {

    Optional<Cliente> findByNome(String nome);
}
