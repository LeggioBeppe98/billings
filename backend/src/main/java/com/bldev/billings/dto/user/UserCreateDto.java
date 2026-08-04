package com.bldev.billings.dto.user;

import com.bldev.billings.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserCreateDto {
    @NotBlank(message = "Il nome è obbligatorio")
    private String nome;

    @NotBlank(message = "Il cognome è obbligatorio")
    private String cognome;

    @NotBlank(message = "L'email è obbligatoria")
    @Email(message = "L'email non è valida")
    private String email;

    private String cellulare;

    @NotBlank(message = "La password è obbligatoria")
    private String password;

    @NotNull(message = "Il ruolo è obbligatorio")
    private Role role;

    @NotNull(message = "Attivo è obbligatorio")
    private Boolean attivo = true;

}
