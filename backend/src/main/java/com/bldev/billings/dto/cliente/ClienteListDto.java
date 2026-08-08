package com.bldev.billings.dto.cliente;

import com.bldev.billings.dto.tariffa.TariffaSummaryDto;

public record ClienteListDto(
        Long id,
        String nome,
        String cognome,
        String codiceFiscale,
        String indirizzoResidenza,
        TariffaSummaryDto tariffa,
        boolean attivo) {
}
