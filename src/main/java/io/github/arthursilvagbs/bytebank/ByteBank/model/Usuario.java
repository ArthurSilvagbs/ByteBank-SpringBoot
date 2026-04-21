package io.github.arthursilvagbs.bytebank.ByteBank.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
public class Usuario {

   @Id
   @Column(nullable = false)
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @Column(name = "login", nullable = false, length = 70)
   private String login;

   @Column(name = "senha", nullable = false, length = 255)
   private String senha;

   @Column(name = "roles")
   private List<String> roles;

   public Usuario(UUID id, String login, String senha, List<String> roles) {
      this.id = id;
      this.login = login;
      this.senha = senha;
      this.roles = roles;
   }
}
