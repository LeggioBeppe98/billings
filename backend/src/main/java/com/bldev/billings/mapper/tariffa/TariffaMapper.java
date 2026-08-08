package com.bldev.billings.mapper.tariffa;

import com.bldev.billings.dto.tariffa.TariffaCreateDto;
import com.bldev.billings.dto.tariffa.TariffaDetailDto;
import com.bldev.billings.dto.tariffa.TariffaListDto;
import com.bldev.billings.dto.tariffa.TariffaUpdateDto;
import com.bldev.billings.model.Tariffa;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TariffaMapper {
    TariffaListDto toListDto(Tariffa tariffa);
    TariffaDetailDto toDetailDto(Tariffa tariffa);
    Tariffa toEntity(TariffaCreateDto dto);
    void updateEntityFromDto(TariffaUpdateDto dto, @MappingTarget Tariffa tariffa);

}
