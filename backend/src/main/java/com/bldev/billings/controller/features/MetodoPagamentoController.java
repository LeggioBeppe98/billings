package com.bldev.billings.controller.features;

import com.bldev.billings.dto.metodipagamento.MetodoPagamentoCreateDto;
import com.bldev.billings.dto.metodipagamento.MetodoPagamentoDetailDto;
import com.bldev.billings.dto.metodipagamento.MetodoPagamentoListDto;
import com.bldev.billings.dto.metodipagamento.MetodoPagamentoUpdateDto;
import com.bldev.billings.service.metodopagamento.MetodoPagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/metodi-di-pagamento")
public class MetodoPagamentoController {
    private final MetodoPagamentoService metodoPagamentoService;

    public MetodoPagamentoController(MetodoPagamentoService metodoPagamentoService) {
        this.metodoPagamentoService = metodoPagamentoService;
    }

    @Operation(summary = "Elenco di tutti i metodi di pagamento", description = "Ritorna l'elenco di tutti i metodi di pagamento. Usa la paginazione")
    @GetMapping
    public Page<MetodoPagamentoListDto> getAllMetodoPagamento(Pageable pageable) {
        return metodoPagamentoService.getAllMetodoPagamento(pageable);
    }

    @Operation(summary = "Ricerca per id del metodo di pagamento")
    @GetMapping("/{id}")
    public MetodoPagamentoDetailDto getMetodoPagamentoById(@PathVariable Long id) {
        return metodoPagamentoService.findMetodoPagamentoById(id);
    }

    @Operation(summary = "Ricerca per attributo attivo a true", description = "Ritorna i metodi di pagamento attivi. Usa la paginazione")
    @GetMapping("/active")
    public Page<MetodoPagamentoListDto> getActiveMetodiPagamento(Pageable pageable) {
        return metodoPagamentoService.findByAttivo(true, pageable);
    }

    @Operation(summary = "Ricerca per attributo attivo a false", description = "Ritorna i metodi di pagamento non attivi. Usa la paginazione")
    @GetMapping("/inactive")
    public Page<MetodoPagamentoListDto> getInactiveMetodiPagamento(Pageable pageable) {
        return metodoPagamentoService.findByAttivo(false, pageable);
    }

    @Operation(summary = "Ricerca per nome", description = "E' una ricerca per LIKE e usa la paginazione")
    @GetMapping("/search/name")
    public Page<MetodoPagamentoListDto> searchByName(@RequestParam String name, Pageable pageable) {
        return metodoPagamentoService.findByNome(name, pageable);
    }

    @Operation(summary = "Crea un nuovo metodo di pagamento", description = "Vincolo di unicità sul nome")
    @PostMapping
    public MetodoPagamentoDetailDto createMetodoPagamento(@Valid @RequestBody MetodoPagamentoCreateDto metodoPagamentoCreateDto) {
        return metodoPagamentoService.createMetodoPagamento(metodoPagamentoCreateDto);
    }

    @Operation(summary = "Aggiorna metodo di pagamento con specifico ID", description = "")
    @PutMapping("/{id}")
    public MetodoPagamentoDetailDto updateMetodoPagamento(@PathVariable Long id, @Valid @RequestBody MetodoPagamentoUpdateDto metodoPagamentoUpdateDto) {
        return metodoPagamentoService.updateMetodoPagamento(id, metodoPagamentoUpdateDto);
    }

    @Operation(summary = "Elimina metodo di pagamento con specifico ID", description = "")
    @DeleteMapping("/{id}")
    public void deleteMetodoPagamento(@PathVariable Long id) {
        metodoPagamentoService.deleteMetodoPagamento(id);
    }
}
