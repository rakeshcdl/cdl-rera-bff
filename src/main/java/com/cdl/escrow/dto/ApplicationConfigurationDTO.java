package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationConfigurationDTO implements Serializable {

    private Long id;

    private String configKey;

    private String configValue;

    private boolean enabled;

    private Boolean deleted ;

}
