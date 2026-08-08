package com.bldev.billings.mapper.metodopagamento;

import com.bldev.billings.dto.metodipagamento.MetodoPagamentoCreateDto;
import com.bldev.billings.dto.metodipagamento.MetodoPagamentoDetailDto;
import com.bldev.billings.dto.metodipagamento.MetodoPagamentoListDto;

import com.bldev.billings.dto.metodipagamento.MetodoPagamentoUpdateDto;
import com.bldev.billings.model.MetodoPagamento;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MetodoPagamentoMapper {
    MetodoPagamentoListDto toListDto(MetodoPagamento metodoPagamento);
    MetodoPagamentoDetailDto toDetailDto(MetodoPagamento metodoPagamento);
    MetodoPagamento toEntity(MetodoPagamentoCreateDto dto);
    void updateEntityFromDto(MetodoPagamentoUpdateDto dto, @MappingTarget MetodoPagamento metodoPagamento);
}
