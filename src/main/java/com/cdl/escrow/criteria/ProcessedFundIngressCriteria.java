/**
 * ProcessedFundIngressCriteria.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 18/07/25
 */


package com.cdl.escrow.criteria;

import com.cdl.escrow.filter.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ProcessedFundIngressCriteria implements Serializable {

    private LongFilter id ;

    private StringFilter pfiTransactionId;

    private DoubleFilter pfiAmount;

    private ZonedDateTimeFilter pfiTransactionDate;

    private StringFilter pfiNarration;

    private StringFilter pfiDescription;

    private BooleanFilter pfiUpdateTas;

    private BooleanFilter pfiTakenRetention;

    private StringFilter pfiRemark;

    private BooleanFilter pfiIsAllocated;

    private StringFilter pfiTransParticular1;

    private StringFilter pfiTransParticular2;

    private StringFilter pfiTransRemark1;

    private StringFilter pfiTransRemark2;

    private StringFilter pfiCheckNumber;

    private BooleanFilter pfiTasUpdated;

    private BooleanFilter pfiTasUpdate;

    private StringFilter pfiUnitRefNumber;

    private DoubleFilter pfiRetentionAmount;

    private StringFilter pfiPrimaryUnitHolderName;

    private StringFilter pfiTasPaymentStatus;

    private StringFilter pfiBatchTransId;

    private StringFilter pfiReconResponse;

    private BooleanFilter pfiIsRoleback;

    private DoubleFilter pfiTotalAmount;

    private BooleanFilter pfiCreditedRetention;

    private StringFilter pfiCbsResponse;

    private StringFilter pfiPaymentRefNo;

    private StringFilter pfiTasRefNo;

    private StringFilter pfiSpecialField1;

    private StringFilter pfiSpecialField2;

    private LongFilter realEstateAssestId;

    private LongFilter capitalPartnerUnitId;

    private LongFilter bucketTypeId;

    private LongFilter bucketSubTypeId;

    private LongFilter depositModeId;

    private LongFilter subDepositTypeId;

    private LongFilter pendingFundIngressId;

    private BooleanFilter enabled ;

    private BooleanFilter deleted;
}
