package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.Nationalized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a real estate project registered under the RERA regulation.
 * Each project is associated with a developer and may have multiple funding and disbursement records.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Jacksonized
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "real_estate_assest")
public class RealEstateAssest implements Serializable {
    @Id
    @SequenceGenerator(
            name = "real_estate_assest_id_seq_gen",
            sequenceName = "real_estate_assest_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "real_estate_assest_id_seq_gen"
    )
    private Long id;

    private String reaId;

    private String reaCif;

    private String reaName;

    @Nationalized
    private String reaNameLocal;

    private String reaLocation;

    private String reaReraNumber;

    private ZonedDateTime reaStartDate;

    private ZonedDateTime reaCompletionDate;

    private String reaPercentComplete;

    private Double reaConstructionCost;

    private ZonedDateTime reaAccStatusDate;

    private ZonedDateTime reaRegistrationDate;

    private Integer reaNoOfUnits;

    private String reaRemarks;

    private String reaSpecialApproval;

    private String reaManagedBy;

    private String reaBackupUser;

    private String reaRetentionPercent;

    private String reaAdditionalRetentionPercent;

    private String reaTotalRetentionPercent;

    private ZonedDateTime reaRetentionEffectiveDate;

    private String reaManagementExpenses;

    private String reaMarketingExpenses;

    private ZonedDateTime reaAccoutStatusDate;

    private String reaTeamLeadName;

    private String reaRelationshipManagerName;

    private String reaAssestRelshipManagerName;

    private Double reaRealEstateBrokerExp;

    private Double reaAdvertisementExp;

    private String reaLandOwnerName;

    @ManyToOne
    @JsonIgnore
    private BuildPartner buildPartner;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting reaStatus;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting reaType;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting reaAccountStatus;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting reaConstructionCostCurrency;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting reaBlockPaymentTypes;

    @OneToMany(mappedBy = "realEstateAssest")
    @JsonIgnore
    private transient Set<BankAccount> bankAccounts ;

    @OneToMany(mappedBy = "realEstateAssest")
    @JsonIgnore
    private Set<RealEstateBankAccount> realEstateBankAccounts ;


    @ManyToMany(mappedBy = "realEstateAssests" , fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<RealEstateAssestBeneficiary> realEstateAssestBeneficiaries = new HashSet<>();

    @OneToMany(mappedBy = "realEstateAssest")
    @JsonIgnore
    private Set<RealEstateAssestClosure> realEstateAssestClosures ;

    @OneToMany(mappedBy = "realEstateAssest")
    @JsonIgnore
    private Set<RealEstateAssestFee> realEstateAssestFees ;

    @OneToMany(mappedBy = "realEstateAssest")
    @JsonIgnore
    private Set<RealEstateAssestFeeHistory> realEstateAssestFeeHistories ;

    @OneToMany(mappedBy = "realEstateAssest")
    @JsonIgnore
    private Set<RealEstateAssestFinancialSummary> realEstateAssestFinancialSummaries ;

    @OneToMany(mappedBy = "realEstateAssest")
    @JsonIgnore
    private Set<CapitalPartnerUnit> capitalPartnerUnits ;

    @OneToMany(mappedBy = "realEstateAssest")
    @JsonIgnore
    private Set<ProcessedFundIngress> processedFundIngresses ;

    @OneToMany(mappedBy = "realEstateAssest")
    @JsonIgnore
    private transient Set<PendingFundIngress> pendingFundIngresses;

    @OneToMany(mappedBy = "realEstateAssest")
    @JsonIgnore
    private Set<FundEgress> fundEgresses ;

    @OneToMany(mappedBy = "realEstateAssest")
    @JsonIgnore
    private Set<SuretyBond> suretyBonds;
    private Boolean enabled ;

    private Boolean deleted;

    @OneToMany(mappedBy = "realEstateAssest")
    @JsonIgnore
    private Set<RealEstateAssestPaymentPlan> realEstateAssestPaymentPlans ;

    @ManyToOne(fetch = FetchType.LAZY)
    private TaskStatus taskStatus;
}
