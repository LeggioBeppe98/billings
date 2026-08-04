package com.bldev.billings.dto.user;

import com.bldev.billings.model.Role;

public record UserListDto(
        Long id,
        String nome,
        String cognome,
        String email,
        Role role,
        boolean attivo
) {}



