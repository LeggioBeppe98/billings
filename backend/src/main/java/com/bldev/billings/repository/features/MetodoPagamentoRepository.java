package com.bldev.billings.repository.features;

import com.bldev.billings.model.MetodoPagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetodoPagamentoRepository extends JpaRepository<MetodoPagamento, Long> {
    boolean existsByNome(String nome);
    Page<MetodoPagamento> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Page<MetodoPagamento> findByAttivo(boolean attivo, Pageable pageable);
}
