package com.cdl.escrow.service;

import com.cdl.escrow.dto.BuildPartnerFeesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BuildPartnerFeesService {
    Page<BuildPartnerFeesDTO> getAllBuildPartnerFees(final Pageable pageable);

    Optional<BuildPartnerFeesDTO> getBuildPartnerFeesById(Long id);

    BuildPartnerFeesDTO saveBuildPartnerFees(BuildPartnerFeesDTO buildPartnerFeesDTO);

    BuildPartnerFeesDTO updateBuildPartnerFees(Long id, BuildPartnerFeesDTO buildPartnerFeesDTO);

    Boolean deleteBuildPartnerFeesById(Long id);

    boolean softBuildPartnerFeesServiceById(Long id);
}
