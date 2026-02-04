package io.github.arthursilvagbs.bytebank.ByteBank.service;

import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.OperacaoNaoPermitidaException;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.ClienteRespository;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.ContaRpository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRespository respository;
    private final ContaRpository contaRpository;

    public Cliente salvar(Cliente cliente){
        return respository.save(cliente);
    }

    public void atualizar(Cliente cliente) {
        if (cliente.getId() == null) {
            throw new IllegalArgumentException("Para atualizar um cliente, é ncessário que ele esteja cadastrado.");
        }
        respository.save(cliente);
    }

    public Optional<Cliente> obterPorId(UUID id) {
        return respository.findById(id);
    }

    public void deletar(Cliente cliente) {
        if (possuiConta(cliente)) {
            throw new OperacaoNaoPermitidaException("O cliente não pode ser deletado, pois ainda existem contas vinculadas a ele.");
        }
        respository.delete(cliente);
    }

    public Optional<Cliente> pesquisaPorNome(String nome) {
        return respository.findByNome(nome);
    }

    public Optional<Cliente> pesquisaPorId(UUID id) {
        return respository.findById(id);
    }

    public List<Cliente> obterTodosOsClientes() {
        return respository.findAll();
    }

    public boolean possuiConta(Cliente cliente) {
        return contaRpository.findByCliente(cliente);
    }

}
