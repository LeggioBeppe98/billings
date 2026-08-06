package com.bldev.billings.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data // Con questa annotazione Lombok mi genera tutti i getter/setter/equals e toString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "tariffe")
public class Tariffe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String nome;

    @Column(nullable = false)
    @NotNull
    @Positive
    @DecimalMin("0.0")
    private BigDecimal importo;

    @Column(name = "periodicita", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private int periodicita;

    @Column(nullable = false)
    private boolean attiva;

    @Column(name = "dcreazione", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime dcreazione;

    @Column(name = "dagg", nullable = false)
    @LastModifiedDate
    private LocalDateTime dagg;

}
