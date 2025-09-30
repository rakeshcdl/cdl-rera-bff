package com.cdl.escrow.service;

import com.cdl.escrow.dto.RealEstateAssestDTO;
import com.cdl.escrow.entity.TaskStatus;
import com.cdl.escrow.enumeration.WorkflowRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RealEstateAssestService {
    Page<RealEstateAssestDTO> getAllRealEstateAssest(final Pageable pageable);

    Optional<RealEstateAssestDTO> getRealEstateAssestById(Long id);

    RealEstateAssestDTO saveRealEstateAssest(RealEstateAssestDTO realEstateAssestDTO);

    RealEstateAssestDTO updateRealEstateAssest(Long id, RealEstateAssestDTO realEstateAssestDTO);

    Boolean deleteRealEstateAssestById(Long id);

    void finalizeRealEstateAssest(Long moduleId, TaskStatus status);

    boolean softRealEstateAssestServiceById(Long id);
}
