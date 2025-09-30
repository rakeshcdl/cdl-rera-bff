package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;

/**
 * Represents a payment or disbursement made from the escrow account.
 * Typically issued to developers or contractors upon project milestones.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Jacksonized
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "fund_egress")
public class FundEgress implements Serializable {
    @Id
    @SequenceGenerator(
            name = "fund_egress_id_seq_gen",
            sequenceName = "fund_egress_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "fund_egress_id_seq_gen"
    )
    private Long id ;

    private String feInvoiceNumber;

    private ZonedDateTime fePaymentDate;

    private Double fePaymentAmount;

    private String feGlAccountNumber;

    private String feGlBranchCode;

    private String feUnitRegistrationFee;

    private String feRemark;

  /*  @Lob
    @Column(name = "fe_response_object" , columnDefinition = "TEXT")
    private String feResponseObject;*/

    @Type(JsonType.class)
    @Column(name = "fe_response_object", columnDefinition = "jsonb")
    private Map<String, Object> feResponseObject;

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

   /* @Lob
    @Column(name = "fe_payment_response_obj", columnDefinition = "TEXT")
    private String fePaymentResponseObj;*/


    @Type(JsonType.class)
    @Column(name = "fe_payment_response_obj", columnDefinition = "jsonb")
    private Map<String, Object> fePaymentResponseObj;

    private String fePaymentStatus;

    private String feResPaymentRefNo;

    private String feResUniqueRefNo;

/*    @Lob
    @Column(name = "res_header", columnDefinition = "TEXT")
    private String feResHeader;*/

    @Type(JsonType.class)
    @Column(name = "res_header", columnDefinition = "jsonb")
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

    private Double feCorporateAmount;

 /*   @Lob
    @Column(name = "fe_error_response_object", columnDefinition = "TEXT")
    private String feErrorResponseObject;*/


    @Type(JsonType.class)
    @Column(name = "fe_error_response_object", columnDefinition = "jsonb")
    private Map<String, Object> feErrorResponseObject;

    private String fePropertyRegistrationFee;

    private Double feBalanceAmount;

    private Boolean feDocVerified;

   /* @Lob
    @Column(name = "fe_payment_body_obj", columnDefinition = "TEXT")
    private String fePaymentBodyObj;
*/

    @Type(JsonType.class)
    @Column(name = "fe_payment_body_obj", columnDefinition = "jsonb")
    private Map<String, Object> fePaymentBodyObj;


    private String feDealRefNo;

    private Boolean feSpecialRate;

    private Double feTreasuryRate;

    private Boolean feBenefFromProject;

    private Double feCorporatePaymentEngFee;

    private Boolean feIsEngineerFee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ApplicationSetting paymentStatusOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ApplicationSetting voucherPaymentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ApplicationSetting voucherPaymentSubType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ApplicationSetting expenseType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ApplicationSetting expenseSubType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ApplicationSetting invoiceCurrency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ApplicationSetting paymentCurrency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ApplicationSetting chargedCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ApplicationSetting paymentMode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ApplicationSetting transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ApplicationSetting beneficiaryFeePayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ApplicationSetting payoutToBeMadeFromCbs;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private RealEstateAssest realEstateAssest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private CapitalPartnerUnit capitalPartnerUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private CapitalPartnerUnit transferCapitalPartnerUnit;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private BuildPartner buildPartner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private RealEstateAssestBeneficiary realEstateAssestBeneficiary;

   /* @OneToMany(mappedBy = "fundEgress", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<RealEstateAssestFeeHistory> realEstateAssestFeeHistories;
*/
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private SuretyBond suretyBond;

    private Boolean enabled ;

    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    private TaskStatus taskStatus;
}
