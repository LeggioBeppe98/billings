package com.bldev.billings.service.cliente;

import com.bldev.billings.dto.cliente.ClienteCreateDto;
import com.bldev.billings.dto.cliente.ClienteDetailDto;
import com.bldev.billings.dto.cliente.ClienteListDto;
import com.bldev.billings.dto.cliente.ClienteUpdateDto;
import com.bldev.billings.exception.ClienteAlreadyExistsException;
import com.bldev.billings.exception.ClienteNotFoundException;
import com.bldev.billings.exception.TariffaNotFoundException;
import com.bldev.billings.mapper.features.ClienteMapper;
import com.bldev.billings.model.Cliente;
import com.bldev.billings.model.Tariffa;
import com.bldev.billings.repository.features.ClienteRepository;
import com.bldev.billings.repository.features.TariffaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final TariffaRepository tariffaRepository;
    private final ClienteMapper clienteMapper;

    public ClienteServiceImpl(ClienteRepository clienteRepository, TariffaRepository tariffaRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.tariffaRepository = tariffaRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public Page<ClienteListDto> getAllClienti(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(clienteMapper::toListDto);
    }

    @Override
    public Page<ClienteListDto> findByAttivo(boolean attivo, Pageable pageable) {
        return clienteRepository.findByAttivo(attivo, pageable).map(clienteMapper::toListDto);
    }

    @Override
    public Page<ClienteListDto> findByNome(String nome, Pageable pageable) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome, pageable).map(clienteMapper::toListDto);
    }

    @Override
    public Page<ClienteListDto> findByCognome(String cognome, Pageable pageable) {
        return clienteRepository.findByCognomeContainingIgnoreCase(cognome, pageable).map(clienteMapper::toListDto);
    }

    @Override
    public ClienteDetailDto findByCodiceFiscale(String codiceFiscale) {
        return clienteRepository.findByCodiceFiscale(codiceFiscale)
                .map(clienteMapper::toDetailDto)
                .orElseThrow(() -> new ClienteNotFoundException(codiceFiscale));
    }

    @Override
    public ClienteDetailDto findClienteById(Long id) {
        return clienteRepository.findById(id)
                .map(clienteMapper::toDetailDto)
                .orElseThrow(() -> new ClienteNotFoundException(id));
    }

    @Override
    public Page<ClienteListDto> findByDataIscrizione(LocalDate dStart, LocalDate dEnd, Pageable pageable) {
        return clienteRepository.findByDataIscrizioneBetween(dStart, dEnd, pageable).map(clienteMapper::toListDto);
    }

    @Override
    public ClienteDetailDto createCliente(ClienteCreateDto dto) {
        // Verificare se il cliente esiste già per codice fiscale
        if (clienteRepository.existsByCodiceFiscale(dto.getCodiceFiscale())) {
            throw new ClienteAlreadyExistsException(dto.getCodiceFiscale());
        }

        // Altrimenti crea l'entity
        Cliente cliente = clienteMapper.toEntity(dto);

        // Crea l'oggetto da salvare basato sull'oggetto entity mappando la tariffa
        if (dto.getTariffaId() != null) {
            Tariffa tariffa = tariffaRepository.findById(dto.getTariffaId())
                    .orElseThrow(() -> new TariffaNotFoundException(dto.getTariffaId()));
            cliente.setTariffa(tariffa);
        }
        // Ritorna l'oggetto salvato
        Cliente saved = clienteRepository.save(cliente);

        return clienteMapper.toDetailDto(saved);
    }

    @Override
    public ClienteDetailDto updateCliente(Long id, ClienteUpdateDto dto) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));

        // Se modifico il codice fiscale verifico che non sia già presente
        if (!cliente.getCodiceFiscale().equals(dto.getCodiceFiscale()) &&
                clienteRepository.existsByCodiceFiscale(dto.getCodiceFiscale())) {
            throw new ClienteAlreadyExistsException(dto.getCodiceFiscale());
        }

        // Se non ricevo la tariffa inserisco null.
        cliente.setTariffa(dto.getTariffaId() != null
                ? tariffaRepository.findById(dto.getTariffaId()).orElseThrow(() -> new TariffaNotFoundException(dto.getTariffaId()))
                : null);

        // Effettuo il mapping tra dto ed entità
        clienteMapper.updateEntityFromDto(dto, cliente);

        // Salvo l'aggiornamento
        Cliente saved = clienteRepository.save(cliente);

        // Ritorno l'oggetto salvato
        return clienteMapper.toDetailDto(saved);
    }

    @Override
    public void deleteCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNotFoundException(id));

        // TODO: implementare soft delete.
        clienteRepository.delete(cliente);
    }
}
