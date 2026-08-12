package com.bldev.billings.repository.features;

import com.bldev.billings.model.Quota;
import com.bldev.billings.model.StatoQuota;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuotaRepository extends JpaRepository<Quota, Long> {
    Page<Quota> findByTariffaId (Long tariffaId, Pageable pageable);
    Page<Quota> findByMeseAndAnno(int mese, int anno, Pageable pageable);
    Page<Quota> findByStato(StatoQuota stato, Pageable pageable);
    Page<Quota> findByClienteId(Long clienteId, Pageable pageable);
    boolean existsByClienteIdAndMeseAndAnno(Long clienteId, int mese,  int anno);
    Optional<Quota> findByClienteIdAndMeseAndAnno(Long clienteId, int mese, int anno);
}
