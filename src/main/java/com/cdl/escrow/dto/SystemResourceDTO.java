package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemResourceDTO implements Serializable {
    private Long id ;

    private String resourceId;

    private String resourceType;

    private String accessLevel;

    private Boolean deleted ;

    private boolean enabled ;
}
