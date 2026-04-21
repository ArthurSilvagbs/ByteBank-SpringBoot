package io.github.arthursilvagbs.bytebank.ByteBank.mappers;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.usuario.UsuarioCreateDto;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Usuario;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UsuarioMapper {
   public Usuario mapearParaUsuario(UsuarioCreateDto dto) {
      return new Usuario(dto.login(), dto.senha(), dto.roles());
   }
}
