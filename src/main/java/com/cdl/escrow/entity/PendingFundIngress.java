/**
 * PendingFundIngress.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 16/07/25
 */


package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Jacksonized
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "pending_fund_ingress")
public class PendingFundIngress {
    @Id
    @SequenceGenerator(
            name = "pending_fund_ingress_id_seq_gen",
            sequenceName = "pending_fund_ingress_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "pending_fund_ingress_id_seq_gen"
    )
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

    @Lob
    @Column(name = "ptfi_cbs_response" , columnDefinition = "TEXT")
    private String ptfiCbsResponse;

    private String ptfiPaymentRefNo;

    @ManyToOne
    @JsonIgnore
    private RealEstateAssest realEstateAssest;

    @ManyToOne
    @JsonIgnore
    private CapitalPartnerUnit capitalPartnerUnit;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting bucketType;


    @ManyToOne
    @JsonIgnore
    private ApplicationSetting depositMode;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting subDepositType;
    private Boolean enabled ;

    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    private TaskStatus taskStatus;
}
