package com.cdl.escrow.service;

import com.cdl.escrow.dto.RealEstateAssestPaymentPlanDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RealEstateAssestPaymentPlanService {

    Page<RealEstateAssestPaymentPlanDTO> getAllRealEstateAssestPaymentPlan(final Pageable pageable);

    Optional<RealEstateAssestPaymentPlanDTO> getRealEstateAssestPaymentPlanById(Long id);

    RealEstateAssestPaymentPlanDTO saveRealEstateAssestPaymentPlan(RealEstateAssestPaymentPlanDTO realEstateAssestPaymentPlanDTO);

    RealEstateAssestPaymentPlanDTO updateRealEstateAssestPaymentPlan(Long id, RealEstateAssestPaymentPlanDTO realEstateAssestPaymentPlanDTO);

    Boolean deleteRealEstateAssestPaymentPlanById(Long id);

    RealEstateAssestPaymentPlanDTO saveAllRealEstateAssestPaymentPlan(@Valid List<RealEstateAssestPaymentPlanDTO> dto);

    boolean softRealEstateAssestPaymentPlanServiceById(Long id);
}
