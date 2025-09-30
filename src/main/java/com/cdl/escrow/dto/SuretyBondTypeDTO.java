package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuretyBondTypeDTO implements Serializable {
    private Long id ;

    private String suretyBondTypeLabelId;

    private String suretyBondTypeId;

    private String suretyBondTypeFetchUrl;

    private Boolean deleted ;

    private boolean enabled ;
}
