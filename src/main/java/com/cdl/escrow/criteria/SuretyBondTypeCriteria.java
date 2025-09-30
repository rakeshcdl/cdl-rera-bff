package com.cdl.escrow.criteria;

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
public class SuretyBondTypeCriteria implements Serializable {

    private LongFilter id ;

    private StringFilter suretyBondTypeLabelId;

    private StringFilter suretyBondTypeId;

    private StringFilter suretyBondTypeFetchUrl;
}
