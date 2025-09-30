package com.cdl.escrow.criteria;

import com.cdl.escrow.filter.DoubleFilter;
import com.cdl.escrow.filter.LongFilter;
import com.cdl.escrow.filter.StringFilter;
import com.cdl.escrow.filter.ZonedDateTimeFilter;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class FinancialInstitutionCriteria implements Serializable {

    private LongFilter id;

    private StringFilter fiName;

    private StringFilter fiAddress;

    private StringFilter fiContactNumber;

    private StringFilter fiCode;

    private StringFilter fiAccountNumber;

    private DoubleFilter fiAccountBalance;

    private StringFilter fiIbanNo;

    private ZonedDateTimeFilter fiOpeningDate;

    private StringFilter fiAccountTitle;

    private StringFilter fiSwiftCode;

    private StringFilter fiRoutingCode;

    private StringFilter fiSchemeType;

   // private Set<BankBranchDTO> branchDTOS;

   // private Set<BankAccountDTO> bankAccountDTOS ;
}
