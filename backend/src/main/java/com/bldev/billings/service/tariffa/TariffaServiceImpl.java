package com.bldev.billings.service.tariffa;

import com.bldev.billings.dto.tariffa.TariffaCreateDto;
import com.bldev.billings.dto.tariffa.TariffaDetailDto;
import com.bldev.billings.dto.tariffa.TariffaListDto;
import com.bldev.billings.dto.tariffa.TariffaUpdateDto;
import com.bldev.billings.exception.TariffaNotFoundException;
import com.bldev.billings.mapper.features.TariffaMapper;
import com.bldev.billings.model.Tariffa;
import com.bldev.billings.repository.features.TariffaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TariffaServiceImpl implements TariffaService {
    private final TariffaRepository tariffaRepository;
    private final TariffaMapper tariffaMapper;

    public TariffaServiceImpl(TariffaRepository tariffaRepository, TariffaMapper tariffaMapper) {
        this.tariffaRepository = tariffaRepository;
        this.tariffaMapper = tariffaMapper;
    }

    @Override
    public Page<TariffaListDto> getAllTariffe(Pageable pageable) {
        return tariffaRepository.findAll(pageable).map(tariffaMapper::toListDto);
    }

    @Override
    public Page<TariffaListDto> findByAttiva(boolean attiva, Pageable pageable) {
        return tariffaRepository.findByAttiva(attiva, pageable).map(tariffaMapper::toListDto);
    }

    @Override
    public Page<TariffaListDto> findByNome(String name, Pageable pageable) {
        return tariffaRepository.findByNomeContainingIgnoreCase(name, pageable).map(tariffaMapper::toListDto);
    }

    @Override
    public TariffaDetailDto findTariffaById(Long id) {
        return tariffaRepository.findById(id)
                .map(tariffaMapper::toDetailDto)
                .orElseThrow(() -> new TariffaNotFoundException(id));
    }

    @Override
    public TariffaDetailDto createTariffa(TariffaCreateDto dto) {
        Tariffa tariffa = tariffaMapper.toEntity(dto);

        Tariffa saved = tariffaRepository.save(tariffa);

        return tariffaMapper.toDetailDto(saved);
    }

    @Override
    public TariffaDetailDto updateTariffa(Long id, TariffaUpdateDto dto) {
        Tariffa tariffa = tariffaRepository.findById(id)
                .orElseThrow(() -> new TariffaNotFoundException(id));

        // Aggiorna i campi tramite mapper
        tariffaMapper.updateEntityFromDto(dto, tariffa);

        Tariffa saved = tariffaRepository.save(tariffa);

        return tariffaMapper.toDetailDto(saved);
    }

    @Override
    public void deleteTariffa(Long id) {
        Tariffa tariffa = tariffaRepository.findById(id)
                .orElseThrow(() -> new TariffaNotFoundException(id));

        // TODO: quando esisterà Quota, verificare che non sia referenziata prima
        // di eliminare fisicamente — altrimenti disattivare (attiva = false)
        tariffaRepository.delete(tariffa);
    }
}