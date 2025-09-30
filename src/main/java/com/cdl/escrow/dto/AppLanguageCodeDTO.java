/**
 * AppLanguageCodeDTO.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 11/06/25
 */


package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppLanguageCodeDTO implements Serializable {

    private Long id;

    private String languageCode; // ISO code, e.g., 'en'

    private String nameKey; // 'English'

    private String nameNativeValue; // 'English', 'Français', 'Deutsch'

    private boolean isRtl ;

    private Boolean deleted ;

    private boolean enabled ;

    //private Set<AppLanguageTranslationDTO> languageTranslations;
}
