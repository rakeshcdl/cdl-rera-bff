package com.cdl.escrow.service;

import com.cdl.escrow.dto.SuretyBondReleaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SuretyBondReleaseService {
    Page<SuretyBondReleaseDTO> getAllSuretyBondRelease(final Pageable pageable);

    Optional<SuretyBondReleaseDTO> getSuretyBondReleaseById(Long id);

    SuretyBondReleaseDTO saveSuretyBondRelease(SuretyBondReleaseDTO suretyBondReleaseDTO);

    SuretyBondReleaseDTO updateSuretyBondRelease(Long id, SuretyBondReleaseDTO suretyBondReleaseDTO);

    Boolean deleteSuretyBondReleaseById(Long id);

    boolean softSuretyBondReleaseServiceById(Long id);
}
