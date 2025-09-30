package com.cdl.escrow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CapitalPartnerUnitPurchaseDTO implements Serializable {
    private Long id ;

    private ZonedDateTime cpuPurchaseDate;

    private Double cpupSaleRate;

    private Double cpuPurchasePrice;

    private Double cpupUnitRegistrationFee;

    private String cpupAgentName;

    private String cpupAgentId;

    private Double cpupGrossSaleprice;

    private Boolean cpupVatApplicable;

    private String cpupDeedNo;

    private String cpupAgreementNo;

    private ZonedDateTime cpupAgreementDate;

    private Boolean cpupSalePurchaseAgreement;

    private Boolean cpupWorldCheck;

    private Double cpupAmtPaidToDevInEscorw;

    private Double cpupAmtPaidToDevOutEscorw;

    private Double cpupTotalAmountPaid;

    private String cpupUnitIban;

    private Boolean cpupOqood;

    private Boolean cpupOqoodPaid;

    private String cpupOqoodAmountPaid;

    private String cpupUnitAreaSize;

    private String cpupForfeitAmount;

    private String cpupDldAmount;

    private String cpupRefundAmount;

    private String cpupRemarks;

    private String cpupTransferredAmount;

    private String cpupUnitNoOtherFormat;

    private Double cpupSalePrice;

    private Boolean cpupProjectPaymentPlan;

    private Boolean cpupReservationBookingForm;

    private Boolean cpupModificationFeeNeeded;

    private ApplicationSettingDTO cpupCreditCurrencyDTO;

    private ApplicationSettingDTO cpuPurchasePriceCurrencyDTO;

    private CapitalPartnerUnitDTO capitalPartnerUnitDTO;

    private Boolean deleted ;

    private boolean enabled ;
}
