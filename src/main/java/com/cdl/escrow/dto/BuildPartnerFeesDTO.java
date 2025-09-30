package com.cdl.escrow.dto;

import com.cdl.escrow.enumeration.WorkflowStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildPartnerFeesDTO implements Serializable {
    private Long id ;

    private Double debitAmount;

    private Double totalAmount;

    private ZonedDateTime feeCollectionDate;

    private ZonedDateTime feeNextRecoveryDate;

    private Double feePercentage;

    private Double vatPercentage;

    private Boolean feeCollected;

    private ApplicationSettingDTO bpFeeCategoryDTO;

    private ApplicationSettingDTO bpFeeFrequencyDTO;

    private ApplicationSettingDTO bpFeeCurrencyDTO;

    private ApplicationSettingDTO bpAccountTypeDTO;

   // private BankAccountDTO debitAccountDTO;

    private WorkflowStatus status;

    private boolean enabled ;

    private BuildPartnerDTO buildPartnerDTO;

    private Boolean deleted ;
}
