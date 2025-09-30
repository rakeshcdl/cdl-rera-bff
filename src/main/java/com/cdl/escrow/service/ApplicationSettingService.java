package com.cdl.escrow.service;

import com.cdl.escrow.dto.ApplicationSettingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ApplicationSettingService {
    Page<ApplicationSettingDTO> getAllApplicationSetting(final Pageable pageable);

    Optional<ApplicationSettingDTO> getApplicationSettingById(Long id);

    ApplicationSettingDTO saveApplicationSetting(ApplicationSettingDTO applicationSettingDTO);

    ApplicationSettingDTO updateApplicationSetting(Long id, ApplicationSettingDTO applicationSettingDTO);

    Boolean deleteApplicationSettingById(Long id);

    boolean softApplicationSettingById(Long id);
}
