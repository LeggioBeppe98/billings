package com.bldev.billings.dto.metodipagamento;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MetodoPagamentoUpdateDto {
    @NotBlank(message = "Nome è obbligatorio")
    private String nome;

    // Non metto il not null perchè se non viene compilata viene inserito il valore di default
    private Boolean attivo = true;
}
