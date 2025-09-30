package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankBranchDTO implements Serializable {
 private Long id ;

 private String bankBranchName;

 private String bankBranchAddress;

 private String bankBranchCode;

 private String bankBranchIfscCode;

 private String bankBranchSwiftCode;

 private String bankBranchRoutingCode;

 private String bankBranchTtcCode;

    private Boolean deleted ;

    private boolean enabled ;

 //private FinancialInstitutionDTO financialInstitutionDTO;

 //private Set<PrimaryBankAccountDTO> bankAccountDTOS;
}
