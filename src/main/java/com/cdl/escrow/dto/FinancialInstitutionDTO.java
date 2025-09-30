package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialInstitutionDTO implements Serializable {
 private Long id;

 private String fiName;

 private String fiAddress;

 private String fiContactNumber;

 private String fiCode;

 private String fiAccountNumber;

 private Double fiAccountBalance;

 private String fiIbanNo;

 private ZonedDateTime fiOpeningDate;

 private String fiAccountTitle;

 private String fiSwiftCode;

 private String fiRoutingCode;

 private String fiSchemeType;

 private Set<BankBranchDTO> branchDTOS;

    private Boolean deleted ;

    private boolean enabled ;
}
