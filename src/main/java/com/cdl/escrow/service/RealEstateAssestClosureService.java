package com.cdl.escrow.service;

import com.cdl.escrow.dto.RealEstateAssestClosureDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RealEstateAssestClosureService {
    Page<RealEstateAssestClosureDTO> getAllRealEstateAssestClosure(final Pageable pageable);

    Optional<RealEstateAssestClosureDTO> getRealEstateAssestClosureById(Long id);

    RealEstateAssestClosureDTO saveRealEstateAssestClosure(RealEstateAssestClosureDTO realEstateAssestClosureDTO);

    RealEstateAssestClosureDTO updateRealEstateAssestClosure(Long id, RealEstateAssestClosureDTO realEstateAssestClosureDTO);

    Boolean deleteRealEstateAssestClosureById(Long id);

    boolean softRealEstateAssestClosureServiceById(Long id);
}
