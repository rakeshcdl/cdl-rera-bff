package com.cdl.escrow.service;

import com.cdl.escrow.dto.BuildPartnerContactDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BuildPartnerContactService {
    Page<BuildPartnerContactDTO> getAllBuildPartnerContact(final Pageable pageable);

    Optional<BuildPartnerContactDTO> getBuildPartnerContactById(Long id);

    BuildPartnerContactDTO saveBuildPartnerContact(BuildPartnerContactDTO buildPartnerContactDTO);

    BuildPartnerContactDTO updateBuildPartnerContact(Long id, BuildPartnerContactDTO buildPartnerContactDTO);

    Boolean deleteBuildPartnerContactById(Long id);

    boolean softBuildPartnerContactServiceById(Long id);
}
