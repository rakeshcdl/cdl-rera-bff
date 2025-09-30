package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationFormDesignDTO implements Serializable {
    private Long id;

    private String formName;

    private String formDefinition;

    private Boolean deleted ;

    private boolean enabled ;
}
