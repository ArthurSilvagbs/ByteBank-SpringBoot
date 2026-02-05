package io.github.arthursilvagbs.bytebank.ByteBank.controller;

import ch.qos.logback.classic.pattern.ClassOfCallerConverter;
import io.github.arthursilvagbs.bytebank.ByteBank.controller.DTO.conta.ContaCreateDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import io.github.arthursilvagbs.bytebank.ByteBank.service.ClienteService;
import io.github.arthursilvagbs.bytebank.ByteBank.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
