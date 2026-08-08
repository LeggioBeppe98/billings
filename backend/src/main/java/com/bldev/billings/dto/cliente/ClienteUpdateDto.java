package com.bldev.billings.dto.cliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteUpdateDto {
    @NotBlank(message = "Nome è obbligatorio")
    private String nome;

    @NotBlank(message = "Cognome è obbligatorio")
    private String cognome;

    @NotBlank(message = "Codice Fiscale è obbligatorio")
    private String codiceFiscale;

    private LocalDate dataNascita;

    private LocalDate dataIscrizione;

    @NotBlank(message = "Indirizzo di Residenza è obbligatorio")
    private String indirizzoResidenza;

    private String cellulare;

    @Email(message = "L'email non è valida")
    private String email;

    private Long tariffaId;

    private Boolean attivo = true;
}
