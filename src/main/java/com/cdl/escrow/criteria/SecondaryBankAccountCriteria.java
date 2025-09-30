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
public class SecondaryBankAccountCriteria implements Serializable {

    private LongFilter id ;

    private StringFilter sbAccountNumber;

    private DoubleFilter sbBalance;

    private StringFilter sbLabelId;

    private StringFilter sbTypeId;

    private LongFilter primaryBankAccountId;

    //private Set<BankAccountDTO> bankAccountDTOS ;
}
