package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeatureFlagDTO implements Serializable {
    private Long id ;

    private String featureName;

    private String featureDescription;

    private Boolean isEnabled;

    private Boolean deleted ;

    private boolean enabled ;
}
