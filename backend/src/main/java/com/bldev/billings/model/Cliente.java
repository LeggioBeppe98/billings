package com.bldev.billings.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data // Con questa annotazione Lombok mi genera tutti i getter/setter/equals e toString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "clienti")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    private String nome;

    @Column(nullable = false)
    @NotBlank
    private String cognome;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String codiceFiscale;

    @Column
    private LocalDate dataNascita;

    @Column
    private LocalDate dataIscrizione;

    @Column(nullable = false)
    @NotBlank
    private String indirizzoResidenza;

    @Column
    private String cellulare;

    @Column
    @Email
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tariffa_id")
    private Tariffa tariffa;

    @Column(nullable = false)
    private Boolean attivo = true;

    @Column(name = "dcreazione", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime dcreazione;

    @Column(name = "dagg", nullable = false)
    @LastModifiedDate
    private LocalDateTime dagg;
}
