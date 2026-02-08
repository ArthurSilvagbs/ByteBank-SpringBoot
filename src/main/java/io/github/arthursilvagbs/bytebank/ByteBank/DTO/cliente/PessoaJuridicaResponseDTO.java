package io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente;

import io.github.arthursilvagbs.bytebank.ByteBank.model.TipoCliente;

import java.util.UUID;

public record PessoaJuridicaResponseDTO(UUID id, String nome, String email, String telefone, String endereco, TipoCliente tipoCliente, String cnpj) {
}
