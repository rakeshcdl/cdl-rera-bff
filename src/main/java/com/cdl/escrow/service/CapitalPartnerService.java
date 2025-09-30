package com.cdl.escrow.service;

import com.cdl.escrow.dto.CapitalPartnerDTO;
import com.cdl.escrow.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CapitalPartnerService {
    Page<CapitalPartnerDTO> getAllCapitalPartner(final Pageable pageable);

    Optional<CapitalPartnerDTO> getCapitalPartnerById(Long id);

    CapitalPartnerDTO saveCapitalPartner(CapitalPartnerDTO capitalPartnerDTO);

    CapitalPartnerDTO updateCapitalPartner(Long id, CapitalPartnerDTO capitalPartnerDTO);

    Boolean deleteCapitalPartnerById(Long id);

    boolean softCapitalPartnerServiceById(Long id);

    void finalizeCapitalPartner(Long moduleId, TaskStatus status);
}
