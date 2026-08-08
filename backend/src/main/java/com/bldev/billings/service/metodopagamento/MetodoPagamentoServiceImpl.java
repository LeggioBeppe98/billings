package com.bldev.billings.service.metodopagamento;

import com.bldev.billings.dto.metodipagamento.MetodoPagamentoCreateDto;
import com.bldev.billings.dto.metodipagamento.MetodoPagamentoDetailDto;
import com.bldev.billings.dto.metodipagamento.MetodoPagamentoListDto;
import com.bldev.billings.dto.metodipagamento.MetodoPagamentoUpdateDto;
import com.bldev.billings.exception.MetodoPagamentoAlreadyExistsException;
import com.bldev.billings.exception.MetodoPagamentoNotFoundException;
import com.bldev.billings.mapper.metodopagamento.MetodoPagamentoMapper;
import com.bldev.billings.model.MetodoPagamento;
import com.bldev.billings.repository.MetodoPagamentoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MetodoPagamentoServiceImpl implements MetodoPagamentoService {
    private final MetodoPagamentoRepository metodoPagamentoRepository;
    private final MetodoPagamentoMapper metodoPagamentoMapper;

    public MetodoPagamentoServiceImpl(MetodoPagamentoRepository metodoPagamentoRepository, MetodoPagamentoMapper metodoPagamentoMapper) {
        this.metodoPagamentoRepository = metodoPagamentoRepository;
        this.metodoPagamentoMapper = metodoPagamentoMapper;
    }

    @Override
    public Page<MetodoPagamentoListDto> getAllMetodoPagamento(Pageable pageable) {
        return metodoPagamentoRepository.findAll(pageable).map(metodoPagamentoMapper::toListDto);
    }

    @Override
    public Page<MetodoPagamentoListDto> findByAttivo(boolean attivo, Pageable pageable) {
        return metodoPagamentoRepository.findByAttivo(attivo, pageable).map(metodoPagamentoMapper::toListDto);
    }

    @Override
    public Page<MetodoPagamentoListDto> findByNome(String nome, Pageable pageable) {
        return metodoPagamentoRepository.findByNomeContainingIgnoreCase(nome, pageable).map(metodoPagamentoMapper::toListDto);
    }

    @Override
    public MetodoPagamentoDetailDto findMetodoPagamentoById(Long id) {
        return metodoPagamentoRepository.findById(id)
                .map(metodoPagamentoMapper::toDetailDto)
                .orElseThrow(() -> new MetodoPagamentoNotFoundException(id));
    }

    @Override
    public MetodoPagamentoDetailDto createMetodoPagamento(MetodoPagamentoCreateDto dto) {

        if (metodoPagamentoRepository.existsByNome(dto.getNome())) {
            throw new MetodoPagamentoAlreadyExistsException(dto.getNome());
        }

        MetodoPagamento metodoPagamento = metodoPagamentoMapper.toEntity(dto);

        MetodoPagamento saved = metodoPagamentoRepository.save(metodoPagamento);

        return metodoPagamentoMapper.toDetailDto(saved);
    }

    @Override
    public MetodoPagamentoDetailDto updateMetodoPagamento(Long id, MetodoPagamentoUpdateDto dto) {

        MetodoPagamento metodoPagamento = metodoPagamentoRepository.findById(id)
                .orElseThrow(() -> new MetodoPagamentoNotFoundException(id));

        // Se modifico il nome, check vincolo unicità
        if(!metodoPagamento.getNome().equals(dto.getNome()) &&
                metodoPagamentoRepository.existsByNome(dto.getNome())) {
            throw  new MetodoPagamentoAlreadyExistsException(dto.getNome());
        }

        metodoPagamentoMapper.updateEntityFromDto(dto, metodoPagamento);

        MetodoPagamento saved = metodoPagamentoRepository.save(metodoPagamento);

        return metodoPagamentoMapper.toDetailDto(saved);
    }

    @Override
    public void deleteMetodoPagamento(Long id) {

        // TODO: Aggiungere soft delete
        MetodoPagamento metodoPagamento = metodoPagamentoRepository.findById(id)
                .orElseThrow(() ->  new MetodoPagamentoNotFoundException(id));

        metodoPagamentoRepository.delete(metodoPagamento);
    }
}
