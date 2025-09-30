package com.cdl.escrow.service;

import com.cdl.escrow.dto.ApplicationConfigurationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ApplicationConfigurationService {

    Page<ApplicationConfigurationDTO> getAllApplicationConfiguration(final Pageable pageable);

    Optional<ApplicationConfigurationDTO> getApplicationConfigurationById(Long id);

    ApplicationConfigurationDTO saveApplicationConfiguration(ApplicationConfigurationDTO applicationConfigurationDTO);

    ApplicationConfigurationDTO updateApplicationConfiguration(Long id, ApplicationConfigurationDTO applicationConfigurationDTO);

    Boolean deleteApplicationConfigurationById(Long id);

    boolean softDeleteApplicationConfigurationById(Long id);

    //Optional<ApplicationConfigurationDTO> findByKeyAndEnabledTrue(String key);
}
