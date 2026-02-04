package io.github.arthursilvagbs.bytebank.ByteBank.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cliente")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", nullable = false, length = 250)
    private String nome;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "telefone", unique = true, nullable = false)
    private String telefone;

    @Column(name = "endereco", nullable = false)
    private String endereco;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Conta> contas;

}
