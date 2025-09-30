/**
 * PendingFundIngressCriteria.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 18/07/25
 */


package com.cdl.escrow.criteria;

import com.cdl.escrow.filter.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class PendingFundIngressCriteria {

    private LongFilter id ;

    private StringFilter ptfiTransactionId;

    private StringFilter ptfiTransactionRefId;

    private DoubleFilter ptfiAmount;

    private DoubleFilter ptfiTotalAmount;

    private ZonedDateTimeFilter ptfiTransactionDate;

    private StringFilter ptfiNarration;

    private StringFilter ptfiDescription;

    private BooleanFilter ptfiDiscard;

    private BooleanFilter ptfiIsAllocated;

    private StringFilter ptfiTransParticular1;

    private StringFilter ptfiTransParticular2;

    private StringFilter ptfiTransRemark1;

    private StringFilter ptfiTransRemark2;

    private StringFilter ptfiCheckNumber;

    private BooleanFilter ptfiTasUpdated;

    private BooleanFilter ptfiTasUpdate;

    private StringFilter ptfiUnitRefNumber;

    private ZonedDateTimeFilter ptfiValueDateTime;

    private ZonedDateTimeFilter ptfiPostedDateTime;

    private ZonedDateTimeFilter ptfiNormalDateTime;

    private StringFilter ptfiBranchCode;

    private StringFilter ptfiPostedBranchCode;

    private StringFilter ptfiCurrencyCode;

    private StringFilter ptfiSpecialField1;

    private StringFilter ptfiSpecialField2;

    private StringFilter ptfiSpecialField3;

    private StringFilter ptfiSpecialField4;

    private StringFilter ptfiSpecialField5;

    private DoubleFilter ptfiRetentionAmount;

    private StringFilter ptfiPrimaryUnitHolderName;

    private BooleanFilter ptfiIsUnAllocatedCategory;

    private StringFilter ptfiTasPaymentStatus;

    private ZonedDateTimeFilter ptfiDiscardedDateTime;

    private BooleanFilter ptfiCreditedEscrow;

    private StringFilter ptfiCbsResponse;

    private StringFilter ptfiPaymentRefNo;

    private LongFilter realEstateAssestId;

    private LongFilter capitalPartnerUnitId;

    private LongFilter bucketTypeId;

    private LongFilter bankAccountId;

    private LongFilter depositModeId;

    private LongFilter subDepositTypeId;

    private BooleanFilter enabled ;

    private BooleanFilter deleted;
}
