package io.github.arthursilvagbs.bytebank.ByteBank.repository;

import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, UUID> {
    List<Movimentacao> findAllByContaOrigem(Conta conta);
}
