/**
 * AppConfigTranslationService.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 11/06/25
 */

package com.cdl.escrow.service;

import com.cdl.escrow.dto.AppLanguageTranslationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AppConfigTranslationService {

    Page<AppLanguageTranslationDTO> getAllAppConfigTranslation(final Pageable pageable);

    Optional<AppLanguageTranslationDTO> getAppConfigTranslationById(Long id);

    AppLanguageTranslationDTO saveAppConfigTranslation(AppLanguageTranslationDTO appConfigTranslationDTO);

    AppLanguageTranslationDTO updateAppConfigTranslation(Long id, AppLanguageTranslationDTO appConfigTranslationDTO);

    Boolean deleteAppConfigTranslationById(Long id);
}
