package com.bldev.billings.dto.metodipagamento;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MetodoPagamentoCreateDto {

    @NotBlank(message = "Nome è obbligatorio")
    private String nome;

    // Non metto not null, perchè se non viene compilato viene inserito il valore di default
    private Boolean attivo = true;

}
