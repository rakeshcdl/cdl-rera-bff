package com.cdl.escrow.service;

import com.cdl.escrow.dto.RealEstateAssestFeeHistoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RealEstateAssestFeeHistoryService {
    Page<RealEstateAssestFeeHistoryDTO> getAllRealEstateAssestFeeHistory(final Pageable pageable);

    Optional<RealEstateAssestFeeHistoryDTO> getRealEstateAssestFeeHistoryById(Long id);

    RealEstateAssestFeeHistoryDTO saveRealEstateAssestFeeHistory(RealEstateAssestFeeHistoryDTO realEstateAssestFeeHistoryDTO);

    RealEstateAssestFeeHistoryDTO updateRealEstateAssestFeeHistory(Long id, RealEstateAssestFeeHistoryDTO realEstateAssestFeeHistoryDTO);

    Boolean deleteRealEstateAssestFeeHistoryById(Long id);

    boolean softRealEstateAssestFeeHistoryServiceById(Long id);
}
