package com.cdl.escrow.service;

import com.cdl.escrow.dto.RealEstateAssestFeeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RealEstateAssestFeeService {
    Page<RealEstateAssestFeeDTO> getAllRealEstateAssestFee(final Pageable pageable);

    Optional<RealEstateAssestFeeDTO> getRealEstateAssestFeeById(Long id);

    RealEstateAssestFeeDTO saveRealEstateAssestFee(RealEstateAssestFeeDTO realEstateAssestFeeDTO);

    RealEstateAssestFeeDTO updateRealEstateAssestFee(Long id, RealEstateAssestFeeDTO realEstateAssestFeeDTO);

    Boolean deleteRealEstateAssestFeeById(Long id);

    boolean softRealEstateAssestFeeServiceById(Long id);
}
