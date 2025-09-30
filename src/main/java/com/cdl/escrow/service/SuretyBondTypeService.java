package com.cdl.escrow.service;

import com.cdl.escrow.dto.SuretyBondTypeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SuretyBondTypeService {
    Page<SuretyBondTypeDTO> getAllSuretyBondType(final Pageable pageable);

    Optional<SuretyBondTypeDTO> getSuretyBondTypeById(Long id);

    SuretyBondTypeDTO saveSuretyBondType(SuretyBondTypeDTO suretyBondTypeDTO);

    SuretyBondTypeDTO updateSuretyBondType(Long id, SuretyBondTypeDTO suretyBondTypeDTO);

    Boolean deleteSuretyBondTypeById(Long id);

    boolean softSuretyBondTypeServiceById(Long id);
}
