package com.cdl.escrow.dto;

import com.cdl.escrow.enumeration.WorkflowStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundEgressDTO implements Serializable {

    private Long id ;

    private String feInvoiceNumber;

    private ZonedDateTime fePaymentDate;

    private Double fePaymentAmount;

    private String feGlAccountNumber;

    private String feGlBranchCode;

    private String feUnitRegistrationFee;

    private String feRemark;

    Map<String, Object> feResponseObject;

    private String fePaymentRefNumber;

    private Boolean feSplitPayment;

    private ZonedDateTime feInvoiceDate;

    private String feRtZeroThree;

    private String feEngineerRefNo;

    private ZonedDateTime feEngineerApprovalDate;

    private String feReraApprovedRefNo;

    private ZonedDateTime feReraApprovedDate;

    private String feInvoiceRefNo;

    private Double feEngineerApprovedAmt;

    private Double feTotalEligibleAmtInv;

    private Double feAmtPaidAgainstInv;

    private String feCapExcedded;

    private Double feTotalAmountPaid;

    private Double feDebitFromEscrow;

    private Double feCurEligibleAmt;

    private Double feDebitFromRetention;

    private Double feTotalPayoutAmt;

    private Double feAmountInTransit;

    private String feVarCapExcedded;

    private Double feIndicativeRate;

    private String fePpcNumber;

    private Boolean feCorporatePayment;

    private String feNarration1;

    private String feNarration2;

    private Double feAmtRecdFromUnitHolder;

    private Boolean feForFeit;

    private Double feForFeitAmt;

    private Boolean feRefundToUnitHolder;

    private Double feRefundAmount;

    private Boolean feTransferToOtherUnit;

    private Double feTransferAmount;

    private String feUnitReraApprovedRefNo;

    private ZonedDateTime feUnitTransferAppDate;

    private Boolean feBeneficiaryToMaster;

    private Double feAmountToBeReleased;

    private ZonedDateTime feBeneDateOfPayment;

    private Double feBeneVatPaymentAmt;

    private Boolean feIncludeInPayout;

    private Double fBbankCharges;

    private Boolean feTasPaymentSuccess;

    private Boolean fetasPaymentRerun;

    private Boolean feDiscardPayment;

    private Boolean feIsTasPayment;

    private Boolean feIsManualPayment;

    private String feSpecialField1;

    private String feSpecialField2;

    private String feSpecialField3;

    private String feUniqueRefNo;

    private Map<String, Object> fePaymentResponseObj;

    private String fePaymentStatus;

    private String feResPaymentRefNo;

    private String feResUniqueRefNo;

    private Map<String, Object> feResHeader;

    private Double feInvoiceValue;

    private String feSpecialField4;

    private String feSpecialField5;

    private String feSpecialField6;

    private String feTasPaymentStatus;

    private String feCorpCertEngFee;

    private String feSpecialField7;

    private Double feCurBalInEscrowAcc;

    private Double feCurBalInRetentionAcc;

    private String feEngineerFeePayment;

    private Double feCorporateAccBalance;

    private Double feSubConsAccBalance;

    private Map<String, Object> feErrorResponseObject;

    private String fePropertyRegistrationFee;

    private Double feBalanceAmount;

    private Boolean feDocVerified;

    private Map<String, Object> fePaymentBodyObj;

    private String feDealRefNo;

    private Boolean feSpecialRate;

    private Double feTreasuryRate;

    private Boolean feBenefFromProject;

    private Double feCorporatePaymentEngFee;

    private Boolean feIsEngineerFee;

    private WorkflowStatus status;

    private Boolean enabled ;

    private Double feCorporateAmount;

   private ApplicationSettingDTO paymentStatusOptionDTO;

   private ApplicationSettingDTO voucherPaymentTypeDTO;

   private ApplicationSettingDTO voucherPaymentSubTypeDTO;

  private ApplicationSettingDTO expenseTypeDTO;

   private ApplicationSettingDTO expenseSubTypeDTO;

   private ApplicationSettingDTO invoiceCurrencyDTO;

    private ApplicationSettingDTO paymentCurrencyDTO;

    private ApplicationSettingDTO chargedCodeDTO;

    private ApplicationSettingDTO paymentModeDTO;

    private ApplicationSettingDTO transactionTypeDTO;

    private ApplicationSettingDTO beneficiaryFeePaymentDTO;

   private ApplicationSettingDTO payoutToBeMadeFromCbsDTO;

    private RealEstateAssestDTO realEstateAssestDTO;

   private CapitalPartnerUnitDTO capitalPartnerUnitDTO;

    private CapitalPartnerUnitDTO transferCapitalPartnerUnitDTO;

   private BuildPartnerDTO buildPartnerDTO;

   private RealEstateAssestBeneficiaryDTO realEstateAssestBeneficiaryDTO;

    //private Set<RealEstateAssestFeeHistoryDTO> realEstateAssestFeeHistoryDTOS;

    private SuretyBondDTO suretyBondDTO;

    private Boolean deleted ;

    private TaskStatusDTO taskStatusDTO;
}
