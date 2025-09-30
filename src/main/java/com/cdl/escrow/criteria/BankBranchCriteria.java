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
public class BankBranchCriteria implements Serializable {

    private LongFilter id ;

    private StringFilter bankBranchName;

    private StringFilter bankBranchAddress;

    private StringFilter bankBranchCode;

    private StringFilter bankBranchIfscCode;

    private StringFilter bankBranchSwiftCode;

    private StringFilter bankBranchRoutingCode;

    private StringFilter bankBranchTtcCode;

    private LongFilter financialInstitutionId;

    //private Set<PrimaryBankAccountDTO> bankAccountDTOS;
}
