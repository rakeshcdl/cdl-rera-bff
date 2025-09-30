/**
 * ProcessedFundIngressDTO.java
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
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessedFundIngressDTO implements Serializable {
    private Long id ;

    private String pfiTransactionId;

    private Double pfiAmount;

    private ZonedDateTime pfiTransactionDate;

    private String pfiNarration;

    private String pfiDescription;

    private Boolean pfiUpdateTas;

    private Boolean pfiTakenRetention;

    private String pfiRemark;

    private Boolean pfiIsAllocated;

    private String pfiTransParticular1;

    private String pfiTransParticular2;

    private String pfiTransRemark1;

    private String pfiTransRemark2;

    private String pfiCheckNumber;

    private Boolean pfiTasUpdated;

    private Boolean pfiTasUpdate;

    private String pfiUnitRefNumber;

    private Double pfiRetentionAmount;

    private String pfiPrimaryUnitHolderName;

    private String pfiTasPaymentStatus;

    private String pfiBatchTransId;

    //  Map<String, Object> pfiReconResponse;

    private Boolean pfiIsRoleback;

    private Double pfiTotalAmount;

    private Boolean pfiCreditedRetention;

    // Map<String, Object> pfiCbsResponse;

    private String pfiPaymentRefNo;

    private String pfiTasRefNo;

    private String pfiSpecialField1;

    private String pfiSpecialField2;

    private RealEstateAssestDTO realEstateAssestDTO;

    private CapitalPartnerUnitDTO capitalPartnerUnitDTO;

    private ApplicationSettingDTO bucketTypeDTO;

    private ApplicationSettingDTO bucketSubTypeDTO;

    private ApplicationSettingDTO depositModeDTO;

    private ApplicationSettingDTO subDepositTypeDTO;

    private Boolean deleted ;

    private boolean enabled ;
    private TaskStatusDTO taskStatusDTO;

    private PendingFundIngressDTO pendingFundIngressDTO;
}
