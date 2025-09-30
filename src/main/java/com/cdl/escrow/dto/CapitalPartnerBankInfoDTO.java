package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapitalPartnerBankInfoDTO implements Serializable {
    private Long id ;

    private String cpbiPayeeName;

    private String cpbiPayeeAddress;

    private String cpbiBankName;

    private String cpbiBankAddress;

    private String cpbiBicCode;

    private String cpbiBeneRoutingCode;

    private String cpbiAccountNumber;

    private String cpbiIban;

    //private  transient BankAccountDTO bankAccountDTO;

    private CapitalPartnerDTO capitalPartnerDTO;

    private ApplicationSettingDTO payModeDTO;
    private Boolean deleted ;

    private boolean enabled ;
}
