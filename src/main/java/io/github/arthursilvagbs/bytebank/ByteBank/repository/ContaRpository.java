package io.github.arthursilvagbs.bytebank.ByteBank.repository;

import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContaRpository extends JpaRepository<Conta, UUID> {

    boolean existsByCliente(Cliente cliente);
    List<Conta> findAllByCliente(Cliente cliente);
    Optional<Conta> findByNumeroConta(Integer numeroConta);
}
