package io.github.arthursilvagbs.bytebank.ByteBank.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Entity
@Table(name = "conta")
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Conta {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private Integer numeroConta;

    @CreatedDate
    @Column(name = "data_cadastro", nullable = false)
    private LocalDate dataCadastro;

    @Min(0)
    @Column(name = "saldo", scale = 2, precision = 11)
    private BigDecimal saldo;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "contaOrigem", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Movimentacao> entradas = new ArrayList<>();

    @OneToMany(mappedBy = "contaDestino", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Movimentacao> saidas = new ArrayList<>();

    @PrePersist
    public void gerarSaldoIncial() {
        this.saldo = BigDecimal.ZERO;
    }

    @PrePersist
    public void gerarNumeroConta() {
        this.numeroConta = new Random().nextInt(90000) + 10000;
    }

}
