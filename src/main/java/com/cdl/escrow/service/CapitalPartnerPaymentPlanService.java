package com.cdl.escrow.service;

import com.cdl.escrow.dto.CapitalPartnerPaymentPlanDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CapitalPartnerPaymentPlanService {

    Page<CapitalPartnerPaymentPlanDTO> getAllCapitalPartnerPaymentPlan(final Pageable pageable);

    Optional<CapitalPartnerPaymentPlanDTO> getRCapitalPartnerPaymentPlanById(Long id);

    CapitalPartnerPaymentPlanDTO saveCapitalPartnerPaymentPlan(CapitalPartnerPaymentPlanDTO capitalPartnerPaymentPlanDTO);

    CapitalPartnerPaymentPlanDTO updateCapitalPartnerPaymentPlan(Long id, CapitalPartnerPaymentPlanDTO capitalPartnerPaymentPlanDTO);

    Boolean deleteCapitalPartnerPaymentPlanById(Long id);

    CapitalPartnerPaymentPlanDTO saveAllCapitalPartnerPaymentPlan(@Valid List<CapitalPartnerPaymentPlanDTO> capitalPartnerPaymentPlanDTOS);

    boolean softCapitalPartnerPaymentPlanServiceById(Long id);
}
