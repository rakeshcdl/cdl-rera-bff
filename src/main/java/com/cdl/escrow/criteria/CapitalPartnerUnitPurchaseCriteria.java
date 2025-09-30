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
public class CapitalPartnerUnitPurchaseCriteria implements Serializable {

    private LongFilter id ;

    private ZonedDateTimeFilter cpuPurchaseDate;

    private DoubleFilter cpupSaleRate;

    private DoubleFilter cpuPurchasePrice;

    private DoubleFilter cpupUnitRegistrationFee;

    private StringFilter cpupAgentName;

    private StringFilter cpupAgentId;

    private DoubleFilter cpupGrossSaleprice;

    private BooleanFilter cpupVatApplicable;

    private StringFilter cpupDeedNo;

    private StringFilter cpupAgreementNo;

    private ZonedDateTimeFilter cpupAgreementDate;

    private BooleanFilter cpupSalePurchaseAgreement;

    private BooleanFilter cpupWorldCheck;

    private DoubleFilter cpupAmtPaidToDevInEscorw;

    private DoubleFilter cpupAmtPaidToDevOutEscorw;

    private DoubleFilter cpupTotalAmountPaid;

    private StringFilter cpupUnitIban;

    private BooleanFilter cpupOqood;

    private BooleanFilter cpupOqoodPaid;

    private StringFilter cpupOqoodAmountPaid;

    private StringFilter cpupUnitAreaSize;

    private StringFilter cpupForfeitAmount;

    private StringFilter cpupDldAmount;

    private StringFilter cpupRefundAmount;

    private StringFilter cpupRemarks;

    private StringFilter cpupTransferredAmount;

    private StringFilter cpupUnitNoOtherFormat;

    private DoubleFilter cpupSalePrice;

    private BooleanFilter cpupProjectPaymentPlan;

    private BooleanFilter cpupReservationBookingForm;

    private LongFilter cpupCreditCurrencyId;

    private LongFilter cpuPurchasePriceCurrencyId;

    private LongFilter capitalPartnerUnitId;
}
