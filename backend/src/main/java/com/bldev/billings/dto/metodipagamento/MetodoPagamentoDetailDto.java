package com.bldev.billings.dto.metodipagamento;

import java.time.LocalDateTime;

public record MetodoPagamentoDetailDto(
        Long id,
        String nome,
        boolean attivo,
        LocalDateTime dcreazione,
        LocalDateTime dagg
) {}
