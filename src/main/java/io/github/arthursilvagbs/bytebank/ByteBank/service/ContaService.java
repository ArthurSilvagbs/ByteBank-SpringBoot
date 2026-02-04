package io.github.arthursilvagbs.bytebank.ByteBank.service;

import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.ClienteRespository;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.ContaRpository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRpository repository;
    private final ClienteRespository clienteRespository;

    public Conta salvar(Conta conta){
        return repository.save(conta);
    }

    public void atualizar(Conta conta) {
        if (conta.getId() == null) {
            throw new IllegalArgumentException("Para atualizar uma conta, é ncessário que ela esteja cadastrada.");
        }
        repository.save(conta);
    }

    public Optional<Conta> obterPorID(UUID id) {
        return repository.findById(id);
    }

    public Optional<Conta> obterPorNumConta(Integer numeroConta) {
        return repository.finfByNumeroConta(numeroConta);
    }

    public void deletar(Conta conta) {
        repository.delete(conta);
    }

    public List<Conta> obterTodasAsContas() {
        return repository.findAll();
    }

    public List<Conta> obterContasPorCliente(Cliente cliente) {
        return repository.findAllByCliente(cliente);
    }

}
