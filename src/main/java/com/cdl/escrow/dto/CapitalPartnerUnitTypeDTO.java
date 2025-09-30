package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapitalPartnerUnitTypeDTO implements Serializable {
    private Long id ;

    private String cputName;

    private byte[] cputIcon;

    private String cputIconContentType;

    private Boolean cputIsStandalone;

    private String cpUnitTypePrefix;

    private String cputExcelFormula;

    private String cputJsFormula;

    private CapitalPartnerUnitTypeDTO capitalPartnerParentUnitTypeDTO;

    private Set<CapitalPartnerUnitTypeDTO> capitalPartnerChildrenUnitTypeDTOS ;

    private Set<CapitalPartnerUnitDTO> capitalPartnerUnitDTOS;

    private Boolean deleted ;

    private boolean enabled ;
}
