package com.cdl.escrow.dto;

import com.cdl.escrow.entity.AppLanguageTranslation;
import com.cdl.escrow.enumeration.WorkflowStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationSettingDTO implements Serializable {
    private Long id ;

    private String settingKey;

    private String settingValue;

    private AppLanguageTranslationDTO languageTranslationId;

    private String remarks;

    private WorkflowStatus status;

    private boolean enabled;

    private Boolean deleted ;
}
