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
public class PrimaryBankAccountCriteria implements Serializable {

    private LongFilter id ;

    private StringFilter pcAccountNumber;

    private DoubleFilter pbBalance;

    private StringFilter pbLabelId;

    private StringFilter pbTypeId;

    //private Set<SecondaryBankAccountDTO> secondaryBankAccountsDTO ;

    //private Set<BankAccountDTO> bankAccountDTOS ;
}
