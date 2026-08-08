package com.bldev.billings.mapper.features;

import com.bldev.billings.dto.cliente.ClienteCreateDto;
import com.bldev.billings.dto.cliente.ClienteDetailDto;
import com.bldev.billings.dto.cliente.ClienteListDto;
import com.bldev.billings.dto.cliente.ClienteUpdateDto;
import com.bldev.billings.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = TariffaMapper.class)
public interface ClienteMapper {
    ClienteListDto toListDto(Cliente cliente);
    ClienteDetailDto toDetailDto(Cliente cliente);

    @Mapping(target = "tariffa", ignore = true)
    Cliente toEntity(ClienteCreateDto clienteCreateDto);

    @Mapping(target = "tariffa", ignore = true)
    void updateEntityFromDto(ClienteUpdateDto dto, @MappingTarget Cliente cliente);
}
