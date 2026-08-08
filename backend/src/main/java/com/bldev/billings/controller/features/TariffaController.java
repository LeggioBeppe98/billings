package com.bldev.billings.controller.features;

import com.bldev.billings.dto.tariffa.TariffaCreateDto;
import com.bldev.billings.dto.tariffa.TariffaDetailDto;
import com.bldev.billings.dto.tariffa.TariffaListDto;
import com.bldev.billings.dto.tariffa.TariffaUpdateDto;
import com.bldev.billings.service.tariffa.TariffaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tariffe")
public class TariffaController {
    private final TariffaService tariffaService;

    public TariffaController(TariffaService tariffaService) {
        this.tariffaService = tariffaService;
    }

    @Operation(summary = "Elenco di tutte le tariffe", description = "Ritorna l'elenco di tutte le tariffe. Usa la paginazione")
    @GetMapping
    public Page<TariffaListDto> getAllTariffe(Pageable pageable) {
        return tariffaService.getAllTariffe(pageable);
    }

    @Operation(summary = "Ricerca per id della Tariffa")
    @GetMapping("/{id}")
    public TariffaDetailDto getTariffaById(@PathVariable Long id) {
        return tariffaService.findTariffaById(id);
    }

    @Operation(summary = "Ricerca per attributo attivo a true", description = "Ritorna le tariffe attive. Usa la paginazione")
    @GetMapping("/active")
    public Page<TariffaListDto> getActiveTariffas(Pageable pageable) {
        return tariffaService.findByAttiva(true, pageable);
    }

    @Operation(summary = "Ricerca per attributo attivo a false", description = "Ritorna le tariffe non attive. Usa la paginazione")
    @GetMapping("/inactive")
    public Page<TariffaListDto> getInactiveTariffas(Pageable pageable) {
        return tariffaService.findByAttiva(false, pageable);
    }

    @Operation(summary = "Ricerca per nome", description = "E' una ricerca per LIKE e usa la paginazione")
    @GetMapping("/search/name")
    public Page<TariffaListDto> searchByName(@RequestParam String name, Pageable pageable) {
        return tariffaService.findByNome(name, pageable);
    }

    @Operation(summary = "Crea una nuova tariffa", description = "")
    @PostMapping
    public TariffaDetailDto createTariffa(@Valid @RequestBody TariffaCreateDto tariffaCreateDto) {
        return tariffaService.createTariffa(tariffaCreateDto);
    }

    @Operation(summary = "Aggiorna tariffa con specifico ID", description = "")
    @PutMapping("/{id}")
    public TariffaDetailDto updateTariffa(@PathVariable Long id, @Valid @RequestBody TariffaUpdateDto tariffaUpdateDto) {
        return tariffaService.updateTariffa(id, tariffaUpdateDto);
    }
    @Operation(summary = "Elimina tariffa con specifico ID", description = "")
    @DeleteMapping("/{id}")
    public void deleteTariffa(@PathVariable Long id) {
        tariffaService.deleteTariffa(id);
    }
}
