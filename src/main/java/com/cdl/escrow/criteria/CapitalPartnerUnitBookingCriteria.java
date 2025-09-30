package com.cdl.escrow.criteria;

import com.cdl.escrow.filter.DoubleFilter;
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
public class CapitalPartnerUnitBookingCriteria implements Serializable {
    private LongFilter id ;

    private DoubleFilter cpubAmountPaid;

    private DoubleFilter cpubAreaSize;

    private DoubleFilter cpubForFeitAmount;

    private DoubleFilter cpubDldAmount;

    private DoubleFilter cpubRefundAmount;

    private StringFilter cpubRemarks;

    private DoubleFilter cpubTransferredAmount;

    private LongFilter capitalPartnerUnitId ;

   // private Set<CapitalPartnerUnitDTO> capitalPartnerUnitDTOS ;
}
