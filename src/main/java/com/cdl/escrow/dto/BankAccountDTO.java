/**
 * BankAccountDTO.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 17/07/25
 */


package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDTO implements Serializable {

    private Long id;

    private String bankAccountNumber;

    private Double bankAccountBalance;

    private String bankAccountIbanNo;

    private ZonedDateTime bankAccountOpenedDate;

    private String bankAccountTitle;

    private String bankAccountSwiftCode;

    private String bankAccountRoutingCode;

    private String bankAccountSchemeType;

    private BankBranchDTO bankBranchDTO;

    private ApplicationSettingDTO bankCurrencyDTO;

    private PrimaryBankAccountDTO primaryBankAccountDTO;

    private SecondaryBankAccountDTO secondaryBankAccountDTO;

    private Boolean deleted ;

    private boolean enabled ;

    //private BuildPartnerBeneficiaryDTO buildPartnerBeneficiaryDTO;

    //private RealEstateAssestDTO realEstateAssestDTO;

   // private RealEstateAssestBeneficiaryDTO realEstateAssestBeneficiaryDTO;

   // private Set<CapitalPartnerBankInfoDTO> capitalPartnerBankInfoDTOS;

   // private CapitalPartnerUnitDTO capitalPartnerUnitDTO;

    //private Set<ProcessedFundIngressDTO> processedFundIngressDTOS;

    //private Set<PendingFundIngressDTO> pendingFundIngressDTOS;

   // private Set<FundEgressDTO> fundEgressDTOS;
}
