package com.cdl.escrow.entity;


import com.cdl.escrow.enumeration.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Represents a deposit transaction into the escrow account for a given project.
 * Tracks the source, amount, and timestamp of each fund ingress.
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
@Table(name = "processed_fund_ingress")
public class ProcessedFundIngress implements Serializable {
    @Id
    @SequenceGenerator(
            name = "processed_fund_ingress_id_seq_gen",
            sequenceName = "processed_fund_ingress_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "processed_fund_ingress_id_seq_gen"
    )
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

    /*@Lob
    @Column(name = "recon_response" , columnDefinition = "TEXT")*/
    @Type(JsonType.class)
    @Column(name = "recon_response", columnDefinition = "jsonb")
    private String pfiReconResponse;

    private Boolean pfiIsRoleback;

    private Double pfiTotalAmount;

    private Boolean pfiCreditedRetention;

   /* @Lob
    @Column(name = "cbs_response" , columnDefinition = "TEXT")*/

    @Type(JsonType.class)
    @Column(name = "cbs_response", columnDefinition = "jsonb")
    private String pfiCbsResponse;

    private String pfiPaymentRefNo;

    private String pfiTasRefNo;

    private String pfiSpecialField1;

    private String pfiSpecialField2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private RealEstateAssest realEstateAssest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private CapitalPartnerUnit capitalPartnerUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ApplicationSetting bucketType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ApplicationSetting bucketSubType;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ApplicationSetting depositMode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ApplicationSetting subDepositType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private  PendingFundIngress pendingFundIngress;
    private Boolean enabled ;

    private Boolean deleted;

}
