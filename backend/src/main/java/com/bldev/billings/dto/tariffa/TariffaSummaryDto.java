package com.bldev.billings.dto.tariffa;



import java.math.BigDecimal;

public record  TariffaSummaryDto (Long id, String nome, BigDecimal importo) {}