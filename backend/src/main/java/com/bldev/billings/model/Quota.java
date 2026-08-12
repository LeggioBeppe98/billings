package com.bldev.billings.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data // Con questa annotazione Lombok mi genera tutti i getter/setter/equals e toString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name="quote", uniqueConstraints=
        @UniqueConstraint(columnNames = {"mese", "anno","cliente_id"}))
public class Quota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Min(1)
    @Max(12)
    private int mese;

    @Column(nullable = false)
    @Min(2023)
    private int anno;

    private LocalDate dataPagamento;

    @Column(nullable = false)
    private BigDecimal importo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatoQuota stato = StatoQuota.NON_PAGATA;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tariffa_id")
    private Tariffa tariffa;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "metodo_pagamento_id")
    private MetodoPagamento metodoPagamento;

    @Column(name = "dcreazione", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime dcreazione;

    @Column(name = "dagg", nullable = false)
    @LastModifiedDate
    private LocalDateTime dagg;
}
