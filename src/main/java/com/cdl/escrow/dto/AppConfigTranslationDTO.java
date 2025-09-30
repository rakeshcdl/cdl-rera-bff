/**
 * AppConfigTranslationDTO.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 11/06/25
 */


package com.cdl.escrow.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppConfigTranslationDTO  implements Serializable {

    private Long id;

    private String configId;

    private String configValue;

    private String content;

    private AppLanguageCodeDTO appLanguageCodeDTO;

    private Boolean deleted ;

}
