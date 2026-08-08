package com.bldev.billings.repository.features;

import com.bldev.billings.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Page<Cliente> findByAttivo(boolean attivo, Pageable pageable);
    Page<Cliente> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Page<Cliente> findByCognomeContainingIgnoreCase(String cognome, Pageable pageable);
    Optional<Cliente> findByCodiceFiscale(String codiceFiscale);
    boolean existsByCodiceFiscale(String codiceFiscale);
    Page<Cliente> findByTariffaId (Long tariffaId, Pageable pageable);
}
