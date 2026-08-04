package com.bldev.billings.dto.user;

import com.bldev.billings.model.Role;

import java.time.LocalDateTime;

public record UserDetailDto(
        Long id,
        String nome,
        String cognome,
        String email,
        String cellulare,
        Role role,
        boolean attivo,
        LocalDateTime dcreazione,
        LocalDateTime dagg
) {}
