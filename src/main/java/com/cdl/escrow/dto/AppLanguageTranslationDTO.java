/**
 * AppLanguageTranslationDTO.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 15/07/25
 */


package com.cdl.escrow.dto;

import com.cdl.escrow.enumeration.WorkflowStatus;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AppLanguageTranslationDTO implements Serializable {

    private Long id;

    private String configId;

    private String configValue;

    private String content;

    private AppLanguageCodeDTO appLanguageCode;

    private ApplicationModuleDTO applicationModuleDTO;

    private WorkflowStatus status;

    private boolean enabled;

    private Boolean deleted ;
}
