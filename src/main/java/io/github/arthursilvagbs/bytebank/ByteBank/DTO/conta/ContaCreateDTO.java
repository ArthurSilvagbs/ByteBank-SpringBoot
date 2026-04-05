package io.github.arthursilvagbs.bytebank.ByteBank.DTO.conta;

import io.github.arthursilvagbs.bytebank.ByteBank.model.Cliente;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Conta;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ContaCreateDTO(
   @Schema(description = "Cliente titular da conta", example = "{ \"id\": \"123e4567-e89b-12d3-a456-426614174000\", \"nome\": \"João Silva\", \"cpf\": \"12345678900\", \"email\": \"joao@email.com\", \"telefone\": \"00912345678\", \"endereco\": \"Rua das Palmeiras, nº 452, Bairro Jardim Alvorada, Brasília\" }")
   @NotBlank(message = "Campo obrigatório")
   Cliente cliente
) {

    public Conta mapearParaCliente() {
        return new Conta(this.cliente);
    }
}
