package com.cdl.escrow.service;

import com.cdl.escrow.dto.ApplicationModuleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ApplicationModuleService {
    Page<ApplicationModuleDTO> getAllApplicationModule(final Pageable pageable);

    Optional<ApplicationModuleDTO> getApplicationModuleById(Long id);

    ApplicationModuleDTO saveApplicationModule(ApplicationModuleDTO applicationModuleDTO);

    ApplicationModuleDTO updateApplicationModule(Long id, ApplicationModuleDTO applicationModuleDTO);

    Boolean deleteApplicationModuleById(Long id);

    boolean softDeleteApplicationModuleById(Long id);
}
