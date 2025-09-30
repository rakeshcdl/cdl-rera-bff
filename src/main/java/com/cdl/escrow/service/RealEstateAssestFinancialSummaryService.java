package com.cdl.escrow.service;

import com.cdl.escrow.dto.RealEstateAssestFinancialSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RealEstateAssestFinancialSummaryService {
    Page<RealEstateAssestFinancialSummaryDTO> getAllRealEstateAssestFinancialSummary(final Pageable pageable);

    Optional<RealEstateAssestFinancialSummaryDTO> getRealEstateAssestFinancialSummaryById(Long id);

    RealEstateAssestFinancialSummaryDTO saveRealEstateAssestFinancialSummary(RealEstateAssestFinancialSummaryDTO realEstateAssestFinancialSummaryDTO);

    RealEstateAssestFinancialSummaryDTO updateRealEstateAssestFinancialSummary(Long id, RealEstateAssestFinancialSummaryDTO realEstateAssestFinancialSummaryDTO);

    Boolean deleteRealEstateAssestFinancialSummaryById(Long id);

    boolean softRealEstateAssestFinancialSummaryServiceById(Long id);
}
