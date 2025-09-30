/**
 * AppLanguageCodeService.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 11/06/25
 */

package com.cdl.escrow.service;

import com.cdl.escrow.dto.AppLanguageCodeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AppLanguageCodeService {

    Page<AppLanguageCodeDTO> getAllAppLanguageCode(final Pageable pageable);

    Optional<AppLanguageCodeDTO> getAppLanguageCodeById(Long id);

    AppLanguageCodeDTO saveAppLanguageCode(AppLanguageCodeDTO appLanguageCodeDTO);

    AppLanguageCodeDTO updateAppLanguageCode(Long id, AppLanguageCodeDTO appLanguageCodeDTO);

    Boolean deleteAppLanguageCodeById(Long id);

    boolean softDeleteAppLanguageCodeById(Long id);
}
