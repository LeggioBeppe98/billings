package com.bldev.billings.dto.tariffa;

import com.bldev.billings.model.Periodicita;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class TariffaCreateDto {

    @NotBlank(message = "Nome è obbligatorio")
    private String nome;

    @NotNull(message = "Importo è obbligatorio")
    private BigDecimal importo;

    @NotNull(message = "Periodicita è obbligatorio")
    private Periodicita periodicita;

    // Non metto not null, perchè se non viene compilato viene inserito il valore di default
    private Boolean attiva = true;

}
