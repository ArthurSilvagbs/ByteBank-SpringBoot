package io.github.arthursilvagbs.bytebank.ByteBank.controller;

import ch.qos.logback.classic.pattern.ClassOfCallerConverter;
import io.github.arthursilvagbs.bytebank.ByteBank.DTO.usuario.UsuarioCreateDto;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Usuario;
import io.github.arthursilvagbs.bytebank.ByteBank.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("usuarios")
@RequiredArgsConstructor
public class UsuarioController {

   private final UsuarioService service;

   @PostMapping
   public ResponseEntity<?> salvarUsuario(@RequestBody UsuarioCreateDto dto) {
      Usuario usuario = service.salvar(dto);

      URI location = ServletUriComponentsBuilder
         .fromCurrentRequest()
         .path("/{id}")
         .buildAndExpand(usuario.getId())
         .toUri();

      return ResponseEntity.created(location).build();
   }
}
