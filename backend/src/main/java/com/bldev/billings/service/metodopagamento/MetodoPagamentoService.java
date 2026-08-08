package com.bldev.billings.service.metodopagamento;


import com.bldev.billings.dto.metodipagamento.MetodoPagamentoCreateDto;
import com.bldev.billings.dto.metodipagamento.MetodoPagamentoDetailDto;
import com.bldev.billings.dto.metodipagamento.MetodoPagamentoListDto;
import com.bldev.billings.dto.metodipagamento.MetodoPagamentoUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MetodoPagamentoService {
    Page<MetodoPagamentoListDto> getAllMetodoPagamento(Pageable pageable);

    Page<MetodoPagamentoListDto> findByAttivo(boolean attivo, Pageable pageable);

    Page<MetodoPagamentoListDto> findByNome(String nome, Pageable pageable);

    MetodoPagamentoDetailDto findMetodoPagamentoById(Long id);

    MetodoPagamentoDetailDto createMetodoPagamento(MetodoPagamentoCreateDto dto);

    MetodoPagamentoDetailDto updateMetodoPagamento(Long id, MetodoPagamentoUpdateDto dto);

    void deleteMetodoPagamento(Long id);
}
