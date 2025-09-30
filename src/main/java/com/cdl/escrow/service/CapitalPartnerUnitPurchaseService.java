package com.cdl.escrow.service;

import com.cdl.escrow.dto.CapitalPartnerUnitPurchaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CapitalPartnerUnitPurchaseService {
    Page<CapitalPartnerUnitPurchaseDTO> getAllCapitalPartnerUnitPurchase(final Pageable pageable);

    Optional<CapitalPartnerUnitPurchaseDTO> getCapitalPartnerUnitPurchaseById(Long id);

    CapitalPartnerUnitPurchaseDTO saveCapitalPartnerUnitPurchase(CapitalPartnerUnitPurchaseDTO capitalPartnerUnitPurchaseDTO);

    CapitalPartnerUnitPurchaseDTO updateCapitalPartnerUnitPurchase(Long id, CapitalPartnerUnitPurchaseDTO capitalPartnerUnitPurchaseDTO);

    Boolean deleteCapitalPartnerUnitPurchaseById(Long id);

    boolean softCapitalPartnerUnitPurchaseServiceById(Long id);
}
