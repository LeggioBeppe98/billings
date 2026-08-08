package com.bldev.billings.repository;

import com.bldev.billings.model.Tariffa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffaRepository extends JpaRepository<Tariffa, Long> {
    Page<Tariffa> findByAttiva(boolean attiva, Pageable pageable);
    Page<Tariffa> findByNomeContainingIgnoreCase(String name, Pageable pageable);
}
