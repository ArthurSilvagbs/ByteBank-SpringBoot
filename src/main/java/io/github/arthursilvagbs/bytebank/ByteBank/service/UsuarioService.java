package io.github.arthursilvagbs.bytebank.ByteBank.service;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.usuario.UsuarioCreateDto;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.RecursoNaoEncontradoException;
import io.github.arthursilvagbs.bytebank.ByteBank.mappers.UsuarioMapper;
import io.github.arthursilvagbs.bytebank.ByteBank.model.Usuario;
import io.github.arthursilvagbs.bytebank.ByteBank.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

   private final UsuarioRepository repository;
   private final PasswordEncoder encoder;
   private final UsuarioMapper mapper;

   public Usuario salvar(UsuarioCreateDto dto) {
      Usuario usuario = mapper.mapearParaUsuario(dto);
      String senha = usuario.getSenha();
      usuario.setSenha(encoder.encode(senha));
      return repository.save(usuario);
   }

   public Usuario obterPorLogin(String login) {
      return repository.findByLogin(login).orElseThrow(() -> new RecursoNaoEncontradoException("Usuario não encontrado"));
   }
}
