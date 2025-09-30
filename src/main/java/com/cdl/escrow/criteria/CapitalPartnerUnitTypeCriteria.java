package com.cdl.escrow.criteria;

import com.cdl.escrow.filter.BooleanFilter;
import com.cdl.escrow.filter.LongFilter;
import com.cdl.escrow.filter.StringFilter;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class CapitalPartnerUnitTypeCriteria implements Serializable {

    private LongFilter id ;

    private StringFilter cputName;

   // private byte[] cputIcon;

    private StringFilter cputIconContentType;

    private BooleanFilter cputIsStandalone;

    private StringFilter cpUnitTypePrefix;

    private StringFilter cputExcelFormula;

    private StringFilter cputJsFormula;

    private LongFilter capitalPartnerParentUnitTypeId;

    //private Set<CapitalPartnerUnitTypeDTO> capitalPartnerChildrenUnitTypeDTOS ;

    //private Set<CapitalPartnerUnitDTO> capitalPartnerUnitDTOS;
}
