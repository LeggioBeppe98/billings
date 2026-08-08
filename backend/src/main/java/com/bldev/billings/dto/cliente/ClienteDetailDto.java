package com.bldev.billings.dto.cliente;

import com.bldev.billings.dto.tariffa.TariffaSummaryDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ClienteDetailDto(
        Long id,
        String nome,
        String cognome,
        String codiceFiscale,
        LocalDate dataNascita,
        LocalDate dataIscrizione,
        String indirizzoResidenza,
        TariffaSummaryDto tariffa,
        String cellulare,
        String email,
        boolean attivo,
        LocalDateTime dcreazione,
        LocalDateTime dagg
) {}
