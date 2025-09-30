package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationModuleDTO implements Serializable {

    private Long id ;

    private String moduleName;

    private String moduleCode;

    private String moduleDescription;

    private boolean isActive;

    private Boolean deleted ;

    private boolean enabled ;
}
