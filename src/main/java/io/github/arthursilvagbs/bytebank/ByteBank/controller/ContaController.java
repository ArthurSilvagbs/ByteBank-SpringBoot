package io.github.arthursilvagbs.bytebank.ByteBank.controller;

import io.github.arthursilvagbs.bytebank.ByteBank.controller.DTO.conta.ContaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.controller.DTO.conta.ContaResponseDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.controller.DTO.conta.ContaUpdateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import io.github.arthursilvagbs.bytebank.ByteBank.service.ClienteService;
import io.github.arthursilvagbs.bytebank.ByteBank.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("contas")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService service;
    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody String idCliente){
        UUID id = UUID.fromString(idCliente);
        Optional<Cliente> cliente = clienteService.obterPorId(id);

        if (cliente.isEmpty()) {
            throw new IllegalArgumentException("O cliente não foi encontrado com o ID inserido.");
        }

        ContaCreateDTO dto = new ContaCreateDTO(cliente.get());
        Conta conta = dto.mapearParaCliente();

        service.salvar(conta);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(conta.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> obterPorId(@PathVariable("id") String id) {
        UUID idConta = UUID.fromString(id);
        Optional<Conta> contaOptional = service.obterPorID(idConta);

        if(contaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Conta conta = contaOptional.get();

        ContaResponseDTO dto = new ContaResponseDTO(
          conta.getId(),
          conta.getNumeroConta(),
          conta.getSaldo(),
          conta.getCliente().getId());

        return ResponseEntity.ok(dto);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizarSaldo(@PathVariable("id") String id, ContaUpdateDTO dto) {
        UUID idConta = UUID.fromString(id);
        Optional<Conta> contaOptional = service.obterPorID(idConta);

        if(contaOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Conta contaEntidade = contaOptional.get();

        contaEntidade.setSaldo(dto.saldo());

        service.atualizar(contaEntidade);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        UUID idConta = UUID.fromString(id);
        Optional<Conta> contaEncontrado = service.obterPorID(idConta);

        if (contaEncontrado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        service.deletar(contaEncontrado.get());

        return ResponseEntity.noContent().build();
    }
}
