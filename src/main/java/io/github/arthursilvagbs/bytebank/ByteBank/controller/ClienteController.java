package io.github.arthursilvagbs.bytebank.ByteBank.controller;

import io.github.arthursilvagbs.bytebank.ByteBank.controller.DTO.cliente.*;
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
    public ResponseEntity<Object> salvarPessoaFisica(@RequestBody PessoaFisicaCreateDTO pessoaFisicaCreateDTO) {

        PessoaFisica novoCliente = pessoaFisicaCreateDTO.mapearParaPessoaFisica();

        service.salvar(novoCliente);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(novoCliente.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PostMapping
    public ResponseEntity<Object> salvarPessoaJuridica(@RequestBody PessoaJuridicaCreateDTO pessoaJuridicaCreateDTO) {

        PessoaJuridica novoCliente = pessoaJuridicaCreateDTO.mapearParaPessoaJuridica();

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
                PessoaFisicaResponseDTO pessoaFisicaResponseDTO = new PessoaFisicaResponseDTO(
                        pf.getId(),
                        pf.getNome(),
                        pf.getEmail(),
                        pf.getTelefone(),
                        pf.getEndereco(),
                        pf.getTipoCliente(),
                        pf.getCpf()
                );

                return ResponseEntity.ok(pessoaFisicaResponseDTO);
            }

            if (clienteEntidade instanceof PessoaJuridica pj) {
                PessoaJuridicaResponseDTO pessoaJuridicaResponseDTO = new PessoaJuridicaResponseDTO(
                        pj.getId(),
                        pj.getNome(),
                        pj.getEmail(),
                        pj.getTelefone(),
                        pj.getEndereco(),
                        pj.getTipoCliente(),
                        pj.getCnpj()
                );

                return ResponseEntity.ok(pessoaJuridicaResponseDTO);
            }

        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody ClienteUpdateDTO dto) {
        UUID idCliente = UUID.fromString(id);
        Optional<Cliente> clienteEncontrado = service.obterPorId(idCliente);

        if (clienteEncontrado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var clienteEntidade = clienteEncontrado.get();
        clienteEntidade.setNome(dto.nome());
        clienteEntidade.setEmail(dto.email());
        clienteEntidade.setTelefone(dto.telefone());
        clienteEntidade.setEndereco(dto.endereco());

        service.salvar(clienteEntidade);

        return ResponseEntity.ok(clienteEntidade);
    }


}
