package com.bldev.billings.mapper.features;

import com.bldev.billings.dto.tariffa.*;
import com.bldev.billings.model.Tariffa;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TariffaMapper {
    TariffaListDto toListDto(Tariffa tariffa);
    TariffaDetailDto toDetailDto(Tariffa tariffa);
    Tariffa toEntity(TariffaCreateDto dto);
    void updateEntityFromDto(TariffaUpdateDto dto, @MappingTarget Tariffa tariffa);

    TariffaSummaryDto toSummaryDto(Tariffa tariffa);
}
