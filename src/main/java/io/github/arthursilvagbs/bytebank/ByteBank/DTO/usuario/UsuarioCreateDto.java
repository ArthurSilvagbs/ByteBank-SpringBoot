package io.github.arthursilvagbs.bytebank.ByteBank.DTO.usuario;

import io.github.arthursilvagbs.bytebank.ByteBank.model.Roles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record UsuarioCreateDto(

   @NotBlank(message = "Campo obrigatório")
   String login,

   @NotBlank(message = "Campo obrigatório")
   String senha,

   @NotNull(message = "Campo obrigatório")
   @NotEmpty(message = "Campo obrigatório")
   List<Roles> roles
) {
}
