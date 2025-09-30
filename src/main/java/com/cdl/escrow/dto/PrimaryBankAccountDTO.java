package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrimaryBankAccountDTO implements Serializable {
    private Long id ;

    private String pcAccountNumber;

    private double pbBalance;

    private String pbLabelId;

    private String pbTypeId;

    private Boolean deleted ;

    private boolean enabled ;

    // private Set<SecondaryBankAccountDTO> secondaryBankAccountsDTO ;

   // private transient Set<BankAccountDTO> bankAccountDTOS ;
}
