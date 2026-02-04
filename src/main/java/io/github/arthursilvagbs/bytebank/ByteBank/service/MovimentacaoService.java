package io.github.arthursilvagbs.bytebank.ByteBank.service;

import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Movimentacao;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.MovimentacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovimentacaoService {

    private final MovimentacaoRepository repository;

    public Movimentacao salvar(Movimentacao movimentacao) {
        return repository.save(movimentacao);
    }

    public Optional<Movimentacao> buscarMovimantacaoPorId(UUID id) {
        return repository.findById(id);
    }

    public List<Movimentacao> obterMovimentacoesPorConta(Conta conta) {
        return repository.findAllByContaOrigem(conta);
    }

}
