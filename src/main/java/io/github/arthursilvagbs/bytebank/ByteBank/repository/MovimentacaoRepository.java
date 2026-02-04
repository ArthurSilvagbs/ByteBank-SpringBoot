package io.github.arthursilvagbs.bytebank.ByteBank.repository;

import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovimentacaoRepository extends JpaRepository<Cliente, UUID> {
}
