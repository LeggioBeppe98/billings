package com.bldev.billings.dto.tariffa;

import com.bldev.billings.model.Periodicita;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record  TariffaDetailDto (
        Long id,
        String nome,
        BigDecimal importo,
        Periodicita periodicita,
        boolean attiva,
        LocalDateTime dcreazione,
        LocalDateTime dagg
) {}
