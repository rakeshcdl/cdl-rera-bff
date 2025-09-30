package com.cdl.escrow.service;

import com.cdl.escrow.dto.BuildPartnerBeneficiaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BuildPartnerBeneficiaryService {
    Page<BuildPartnerBeneficiaryDTO> getAllBuildPartnerBeneficiary(final Pageable pageable);

    Optional<BuildPartnerBeneficiaryDTO> getBuildPartnerBeneficiaryById(Long id);

    BuildPartnerBeneficiaryDTO saveBuildPartnerBeneficiary(BuildPartnerBeneficiaryDTO buildPartnerBeneficiaryDTO);

    BuildPartnerBeneficiaryDTO updateBuildPartnerBeneficiary(Long id, BuildPartnerBeneficiaryDTO buildPartnerBeneficiaryDTO);

    Boolean deleteBuildPartnerBeneficiaryById(Long id);

    boolean softBuildPartnerBeneficiaryServiceById(Long id);
}
