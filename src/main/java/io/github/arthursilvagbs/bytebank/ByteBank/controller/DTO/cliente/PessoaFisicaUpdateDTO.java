package io.github.arthursilvagbs.bytebank.ByteBank.controller.DTO.cliente;

import io.github.arthursilvagbs.bytebank.ByteBank.model.TipoCliente;

import java.util.UUID;

public record PessoaFisicaUpdateDTO(String nome, String email, String telefone, String endereco) {
}
