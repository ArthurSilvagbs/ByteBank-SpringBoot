package io.github.arthursilvagbs.bytebank.ByteBank.security;

import io.github.arthursilvagbs.bytebank.ByteBank.model.Usuario;
import io.github.arthursilvagbs.bytebank.ByteBank.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

   private final UsuarioService service;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Usuario usuario = service.obterPorLogin(username);

      if (usuario == null) {
         throw new UsernameNotFoundException("Usuario não encontrado!");
      }

      return User.builder()
         .username(usuario.getLogin())
         .password(usuario.getSenha())
         .roles(usuario.getRoles().toArray(new String[usuario.getRoles().size()]))
         .build();
   }
}
