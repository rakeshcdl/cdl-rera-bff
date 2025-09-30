/**
 * PendingFundIngressDTO.java
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
public class PendingFundIngressDTO implements Serializable {

    private Long id ;

    private String ptfiTransactionId;

    private String ptfiTransactionRefId;

    private Double ptfiAmount;

    private Double ptfiTotalAmount;

    private ZonedDateTime ptfiTransactionDate;

    private String ptfiNarration;

    private String ptfiDescription;

    private Boolean ptfiDiscard;

    private Boolean ptfiIsAllocated;

    private String ptfiTransParticular1;

    private String ptfiTransParticular2;

    private String ptfiTransRemark1;

    private String ptfiTransRemark2;

    private String ptfiCheckNumber;

    private Boolean ptfiTasUpdated;

    private Boolean ptfiTasUpdate;

    private String ptfiUnitRefNumber;

    private ZonedDateTime ptfiValueDateTime;

    private ZonedDateTime ptfiPostedDateTime;

    private ZonedDateTime ptfiNormalDateTime;

    private String ptfiBranchCode;

    private String ptfiPostedBranchCode;

    private String ptfiCurrencyCode;

    private String ptfiSpecialField1;

    private String ptfiSpecialField2;

    private String ptfiSpecialField3;

    private String ptfiSpecialField4;

    private String ptfiSpecialField5;

    private Double ptfiRetentionAmount;

    private String ptfiPrimaryUnitHolderName;

    private Boolean ptfiIsUnAllocatedCategory;

    private String ptfiTasPaymentStatus;

    private ZonedDateTime ptfiDiscardedDateTime;

    private Boolean ptfiCreditedEscrow;

    private String ptfiCbsResponse;

    private String ptfiPaymentRefNo;

    private RealEstateAssestDTO realEstateAssestDTO;

    private CapitalPartnerUnitDTO capitalPartnerUnitDTO;

    private ApplicationSettingDTO bucketTypeDTO;

    private ApplicationSettingDTO depositModeDTO;

    private ApplicationSettingDTO subDepositTypeDTO;

    private Boolean deleted ;

    private boolean enabled ;
    private TaskStatusDTO taskStatusDTO;
}
