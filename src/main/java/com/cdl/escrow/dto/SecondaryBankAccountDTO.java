package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecondaryBankAccountDTO implements Serializable {
    private Long id ;

    private String sbAccountNumber;

    private double sbBalance;

    private String sbLabelId;

    private String sbTypeId;

    private PrimaryBankAccountDTO primaryBankAccountDTO;

    private Boolean deleted ;

    private boolean enabled ;

    // private transient Set<BankAccountDTO> bankAccountDTOS ;
}
