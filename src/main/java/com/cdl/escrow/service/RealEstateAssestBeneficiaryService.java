package com.cdl.escrow.service;

import com.cdl.escrow.dto.RealEstateAssestBeneficiaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RealEstateAssestBeneficiaryService {
    Page<RealEstateAssestBeneficiaryDTO> getAllRealEstateAssestBeneficiary(final Pageable pageable);

    Optional<RealEstateAssestBeneficiaryDTO> getRealEstateAssestBeneficiaryById(Long id);

    RealEstateAssestBeneficiaryDTO saveRealEstateAssestBeneficiary(RealEstateAssestBeneficiaryDTO realEstateAssestBeneficiaryDTO);

    RealEstateAssestBeneficiaryDTO updateRealEstateAssestBeneficiary(Long id, RealEstateAssestBeneficiaryDTO realEstateAssestBeneficiaryDTO);

    Boolean deleteRealEstateAssestBeneficiaryById(Long id);

    boolean softRealEstateAssestBeneficiaryServiceById(Long id);
}
