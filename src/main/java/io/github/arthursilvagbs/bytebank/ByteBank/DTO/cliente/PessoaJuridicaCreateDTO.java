package io.github.arthursilvagbs.bytebank.ByteBank.DTO.cliente;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;

@Schema(description = "Dados de resposta de Pessoa Juridica")
public record PessoaJuridicaCreateDTO(
   @Schema(description = "Nome do cliente", example = "João Pedro Silva")
   @NotBlank(message = "Nome é um campo obrigatório")
   String nome,

   @Schema(description = "Email do cliente", example = "joaosilva@email.com")
   @NotBlank(message = "Email é um campo obrigatório")
   @Email(message = "Deve ser um email válido")
   String email,

   @Schema(description = "Telefone do cliente", example = "00912345678")
   @NotBlank(message = "Telefone é um campo obrigatório")
   String telefone,

   @Schema(description = "Endereço do clinete", example = "Rua das Palmeiras, nº 452, Bairro Jardim Alvorada, Brasília")
   @NotBlank(message = "Endereço é um campo obrigatório")
   String endereco,

   @Schema(description = "CNPJ do cliente", example = "42105938000172")
   @NotBlank(message = "CNPJ é um campo obrigatório")
   @CNPJ(message = "Precisa ser um CNPJ válido")
   String cnpj
) {}
