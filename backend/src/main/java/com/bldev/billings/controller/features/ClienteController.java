package com.bldev.billings.controller.features;

import com.bldev.billings.dto.cliente.ClienteCreateDto;
import com.bldev.billings.dto.cliente.ClienteDetailDto;
import com.bldev.billings.dto.cliente.ClienteListDto;
import com.bldev.billings.dto.cliente.ClienteUpdateDto;
import com.bldev.billings.service.cliente.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/clienti")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Elenco di tutti i clienti", description = "Ritorna l'elenco di tutti i clienti. Usa la paginazione")
    @GetMapping
    public Page<ClienteListDto> getAllClienti(Pageable pageable) {
        return clienteService.getAllClienti(pageable);
    }

    @Operation(summary = "Ricerca per id del cliente")
    @GetMapping("/{id}")
    public ClienteDetailDto getClienteById(@PathVariable Long id) {
        return clienteService.findClienteById(id);
    }

    @Operation(summary = "Ricerca per attributo attivo a true", description = "Ritorna i clienti attivi. Usa la paginazione")
    @GetMapping("/active")
    public Page<ClienteListDto> getActiveCliente(Pageable pageable) {
        return clienteService.findByAttivo(true, pageable);
    }

    @Operation(summary = "Ricerca per attributo attivo a false", description = "Ritorna i clienti non attivi. Usa la paginazione")
    @GetMapping("/inactive")
    public Page<ClienteListDto> getInactiveCliente(Pageable pageable) {
        return clienteService.findByAttivo(false, pageable);
    }

    @Operation(summary = "Ricerca per nome", description = "Usa la paginazione")
    @GetMapping("/search/nome")
    public Page<ClienteListDto> getClienteByNome(@RequestParam String nome, Pageable pageable) {
        return clienteService.findByNome(nome, pageable);
    }

    @Operation(summary = "Ricerca per cognome", description = "Usa la paginazione")
    @GetMapping("/search/cognome")
    public Page<ClienteListDto> getClienteByCognome(@RequestParam String cognome, Pageable pageable) {
        return clienteService.findByCognome(cognome, pageable);
    }

    @Operation(summary = "Ricerca per codice fiscale", description = "Usa la paginazione")
    @GetMapping("/codice-fiscale/{codiceFiscale}")
    public ClienteDetailDto getClienteByCodiceFiscale(@PathVariable String codiceFiscale) {
        return clienteService.findByCodiceFiscale(codiceFiscale);
    }

    @Operation(summary = "Ricerca per data iscrizione", description = "Usa la paginazione")
    @GetMapping("/search/data-iscrizione")
    public Page<ClienteListDto> getClienteByDataIscrizione(@RequestParam LocalDate start, @RequestParam LocalDate end, Pageable pageable) {
        return clienteService.findByDataIscrizione(start, end, pageable);
    }

    @Operation(summary = "Crea un nuovo cliente", description = "")
    @PostMapping
    public ClienteDetailDto createCliente(@Valid @RequestBody ClienteCreateDto clienteCreateDto) {
        return clienteService.createCliente(clienteCreateDto);
    }

    @Operation(summary = "Aggiorna un cliente", description = "")
    @PutMapping("/{id}")
    public ClienteDetailDto updateCliente(@PathVariable Long id, @Valid @RequestBody ClienteUpdateDto clienteUpdateDto) {
        return clienteService.updateCliente(id, clienteUpdateDto);
    }

    @Operation(summary = "Elimina cliente con specifico ID", description = "")
    @DeleteMapping("/{id}")
    public void deleteCliente(@PathVariable Long id) {
        clienteService.deleteCliente(id);
    }

}
