package com.bldev.billings.service.tariffa;

import com.bldev.billings.dto.tariffa.TariffaCreateDto;
import com.bldev.billings.dto.tariffa.TariffaDetailDto;
import com.bldev.billings.dto.tariffa.TariffaListDto;
import com.bldev.billings.dto.tariffa.TariffaUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TariffaService {
    Page<TariffaListDto> getAllTariffe(Pageable pageable);

    Page<TariffaListDto> findByAttiva(boolean attiva, Pageable pageable);

    Page<TariffaListDto> findByNome(String nome, Pageable pageable);

    TariffaDetailDto findTariffaById(Long id);

    TariffaDetailDto createTariffa(TariffaCreateDto dto);

    TariffaDetailDto updateTariffa(Long id, TariffaUpdateDto dto);

    void deleteTariffa(Long id);
}
