package io.github.arthursilvagbs.bytebank.ByteBank.controller;

import io.github.arthursilvagbs.bytebank.ByteBank.controller.DTO.cliente.PessoaFisicaDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.controller.DTO.cliente.PessoaJuridicaDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.model.PessoaFisica;
import io.github.arthursilvagbs.bytebank.ByteBank.model.PessoaJuridica;
import io.github.arthursilvagbs.bytebank.ByteBank.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<Object> salvarPessoaFisica(@RequestBody PessoaFisicaDTO pessoaFisicaDTO) {

        PessoaFisica novoCliente = pessoaFisicaDTO.mapearParaPessoaFisica();

        service.salvar(novoCliente);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoCliente.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping
    public ResponseEntity<Object> salvarPessoaJuridica(@RequestBody PessoaJuridicaDTO pessoaJuridicaDTO) {

        PessoaJuridica novoCliente = pessoaJuridicaDTO.mapearParaPessoaJuridica();

        service.salvar(novoCliente);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoCliente.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }


    @GetMapping("{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable("id") String id) {

        UUID uuid = UUID.fromString(id);

        Optional<Cliente> clienteEncontrado = service.obterPorId(uuid);

        if (clienteEncontrado.isPresent()) {
            Cliente clienteEntidade = clienteEncontrado.get();

            if (clienteEntidade instanceof PessoaFisica pf) {
                PessoaFisicaDTO pessoaFisicaDTO = new PessoaFisicaDTO(
                        pf.getNome(),
                        pf.getEmail(),
                        pf.getTelefone(),
                        pf.getEndereco(),
                        pf.getTipoCliente(),
                        pf.getCpf()
                );

                return ResponseEntity.ok(pessoaFisicaDTO);
            }

            if (clienteEntidade instanceof PessoaJuridica pj) {
                PessoaJuridicaDTO pessoaJuridicaDTO = new PessoaJuridicaDTO(
                        pj.getNome(),
                        pj.getEmail(),
                        pj.getTelefone(),
                        pj.getEndereco(),
                        pj.getTipoCliente(),
                        pj.getCnpj()
                );

                return ResponseEntity.ok(pessoaJuridicaDTO);
            }

        }

        return ResponseEntity.notFound().build();
    }
}
