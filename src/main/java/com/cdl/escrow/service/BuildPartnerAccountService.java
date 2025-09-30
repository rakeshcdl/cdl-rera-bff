package com.cdl.escrow.service;

import com.cdl.escrow.dto.BuildPartnerAccountDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BuildPartnerAccountService {
    Page<BuildPartnerAccountDTO> getAllBuildPartnerAccount(final Pageable pageable);

    Optional<BuildPartnerAccountDTO> getBuildPartnerAccountById(Long id);

    BuildPartnerAccountDTO saveBuildPartnerAccount(BuildPartnerAccountDTO buildPartnerAccountDTO);

    BuildPartnerAccountDTO updateBuildPartnerAccount(Long id, BuildPartnerAccountDTO buildPartnerAccountDTO);

    Boolean deleteBuildPartnerAccountById(Long id);

    boolean softBuildPartnerAccountServiceById(Long id);
}
