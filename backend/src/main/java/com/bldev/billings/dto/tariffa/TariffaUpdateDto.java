package com.bldev.billings.dto.tariffa;

import com.bldev.billings.model.Periodicita;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TariffaUpdateDto {
    @NotBlank(message = "Nome è obbligatorio")
    private String nome;

    @NotNull(message = "Importo è obbligatorio")
    private BigDecimal importo;

    // Non metto il not null perchè se non viene compilata viene isnerito il valore di default
    private Boolean attiva = true;
}
