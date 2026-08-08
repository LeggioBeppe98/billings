package com.bldev.billings.service.cliente;

import com.bldev.billings.dto.cliente.ClienteCreateDto;
import com.bldev.billings.dto.cliente.ClienteDetailDto;
import com.bldev.billings.dto.cliente.ClienteListDto;
import com.bldev.billings.dto.cliente.ClienteUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ClienteService {
    Page<ClienteListDto> getAllClienti(Pageable pageable);

    Page<ClienteListDto> findByAttivo(boolean attivo, Pageable pageable);

    Page<ClienteListDto> findByNome(String nome, Pageable pageable);

    Page<ClienteListDto> findByCognome(String cognome, Pageable pageable);

    ClienteDetailDto findByCodiceFiscale(String codiceFiscale);

    ClienteDetailDto findClienteById(Long id);

    Page<ClienteListDto> findByDataIscrizione(LocalDate dStart, LocalDate dEnd, Pageable pageable);

    ClienteDetailDto createCliente(ClienteCreateDto dto);

    ClienteDetailDto updateCliente(Long id, ClienteUpdateDto dto);

    void deleteCliente(Long id);
}
