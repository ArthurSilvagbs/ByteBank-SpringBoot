package io.github.arthursilvagbs.bytebank.ByteBank.DTO.usuario;

import java.util.List;

public record UsuarioCreateDto(
   String login,
   String senha,
   List<String> roles
) {
}
