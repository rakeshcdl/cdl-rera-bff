package com.cdl.escrow.service;

import com.cdl.escrow.dto.SuretyBondDTO;
import com.cdl.escrow.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SuretyBondService {
    Page<SuretyBondDTO> getAllSuretyBond(final Pageable pageable);

    Optional<SuretyBondDTO> getSuretyBondById(Long id);

    SuretyBondDTO saveSuretyBond(SuretyBondDTO suretyBondDTO);

    SuretyBondDTO updateSuretyBond(Long id, SuretyBondDTO suretyBondDTO);

    Boolean deleteSuretyBondById(Long id);

    boolean softSuretyBondServiceById(Long id);

    void finalizeSuretyBond(Long moduleId, TaskStatus status);
}
