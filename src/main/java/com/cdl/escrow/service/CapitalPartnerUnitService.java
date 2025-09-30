package com.cdl.escrow.service;

import com.cdl.escrow.dto.CapitalPartnerUnitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CapitalPartnerUnitService {
    Page<CapitalPartnerUnitDTO> getAllCapitalPartnerUnit(final Pageable pageable);

    Optional<CapitalPartnerUnitDTO> getCapitalPartnerUnitById(Long id);

    CapitalPartnerUnitDTO saveCapitalPartnerUnit(CapitalPartnerUnitDTO capitalPartnerUnitDTO);

    CapitalPartnerUnitDTO updateCapitalPartnerUnit(Long id, CapitalPartnerUnitDTO capitalPartnerUnitDTO);

    Boolean deleteCapitalPartnerUnitById(Long id);

    boolean softCapitalPartnerUnitServiceById(Long id);
}
