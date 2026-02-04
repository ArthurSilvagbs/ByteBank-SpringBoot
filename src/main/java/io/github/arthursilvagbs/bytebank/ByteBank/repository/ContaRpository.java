package io.github.arthursilvagbs.bytebank.ByteBank.repository;

import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ContaRpository extends JpaRepository<Conta, UUID> {
    boolean findByCliente(Cliente cliente);
}
