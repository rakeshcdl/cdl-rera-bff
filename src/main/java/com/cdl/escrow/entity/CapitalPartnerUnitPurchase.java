package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Represents a unit purchase transaction by a Capital Partner.
 * Tracks payment status, ownership, and financial details.
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
@Table(name = "capital_partner_unit_purchase")
public class CapitalPartnerUnitPurchase implements Serializable {
    @Id
    @SequenceGenerator(
            name = "capital_partner_unit_purchase_id_seq_gen",
            sequenceName = "capital_partner_unit_purchase_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "capital_partner_unit_purchase_id_seq_gen"
    )
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

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting cpupCreditCurrency;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting cpuPurchasePriceCurrency;

    @ManyToOne
    @JsonIgnore
    private CapitalPartnerUnit capitalPartnerUnit;

    private Boolean enabled ;

    private Boolean deleted;
}
