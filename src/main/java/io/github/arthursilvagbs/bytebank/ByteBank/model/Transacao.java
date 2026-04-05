package io.github.arthursilvagbs.bytebank.ByteBank.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transacao")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
public class Transacao {

   @Id
   @Column(name = "id", nullable = false)
   private UUID id;

   @Min(0)
   @Column(name = "valor", nullable = false, scale = 2, precision = 11)
   private BigDecimal valor;

   @ManyToOne
   @JoinColumn(name = "conta_origem_id")
   private Conta contaOrigem;

   @ManyToOne
   @JoinColumn(name = "conta_destino_id")
   private Conta contaDestino;

   @CreatedDate
   @Column(name = )
   private LocalDateTime dataHora

   public Transacao() {}

   public Transacao(Conta contaOrigem, Conta contaDestino, BigDecimal valor) {

   }
}
