package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationTableDesignDTO implements Serializable {
    private Long id;

    private String tableName;

    private String tableDefinition;

    private Boolean deleted ;

    private boolean enabled ;
}
