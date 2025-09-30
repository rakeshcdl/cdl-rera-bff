package com.cdl.escrow.service;

import com.cdl.escrow.dto.SuretyBondRecoveryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SuretyBondRecoveryService {
    Page<SuretyBondRecoveryDTO> getAllSuretyBondRecovery(final Pageable pageable);

    Optional<SuretyBondRecoveryDTO> getSuretyBondRecoveryById(Long id);

    SuretyBondRecoveryDTO saveSuretyBondRecovery(SuretyBondRecoveryDTO suretyBondRecoveryDTO);

    SuretyBondRecoveryDTO updateSuretyBondRecovery(Long id, SuretyBondRecoveryDTO suretyBondRecoveryDTO);

    Boolean deleteSuretyBondRecoveryById(Long id);

    boolean softSuretyBondRecoveryServiceById(Long id);
}
