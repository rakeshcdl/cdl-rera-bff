package com.cdl.escrow.service;

import com.cdl.escrow.dto.CapitalPartnerUnitTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CapitalPartnerUnitTypeService {
    Page<CapitalPartnerUnitTypeDTO> getAllCapitalPartnerUnitType(final Pageable pageable);

    Optional<CapitalPartnerUnitTypeDTO> getCapitalPartnerUnitTypeById(Long id);

    CapitalPartnerUnitTypeDTO saveCapitalPartnerUnitType(CapitalPartnerUnitTypeDTO capitalPartnerUnitTypeDTO);

    CapitalPartnerUnitTypeDTO updateCapitalPartnerUnitType(Long id, CapitalPartnerUnitTypeDTO capitalPartnerUnitTypeDTO);

    Boolean deleteCapitalPartnerUnitTypeById(Long id);

    boolean softCapitalPartnerUnitTypeServiceById(Long id);
}
