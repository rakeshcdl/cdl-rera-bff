package com.cdl.escrow.criteria;

import com.cdl.escrow.filter.DoubleFilter;
import com.cdl.escrow.filter.LongFilter;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class SuretyBondRecoveryCriteria implements Serializable {
    private LongFilter id ;

    private DoubleFilter suretyBondRecoveryReductionAmount;

    private DoubleFilter suretyBondRecoveryBalanceAmount;

    private LongFilter suretyBondId;
}
