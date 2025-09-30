package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the designated beneficiary of a Real Estate Asset,
 * typically a contractor, vendor, or developer receiving escrow disbursements.
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
@Table(name = "real_estate_assest_beneficiary")
public class RealEstateAssestBeneficiary implements Serializable {
    @Id
    @SequenceGenerator(
            name = "real_estate_assest_beneficiary_id_seq_gen",
            sequenceName = "real_estate_assest_beneficiary_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "real_estate_assest_beneficiary_id_seq_gen"
    )
    private Long id;

    private String reabBeneficiaryId;

    private String reabName;

    private Double reabContractAmount;

    private Double reabActualLandPrice;

    private String reabContractorName;

    private String reabType;

    private String reabBank;

    private String reabSwift;

    private String reabRoutingCode;

    private String reabAddress;

    private String reabBankAddress;

    private Boolean reabIsActive;

    private Boolean reabIsDeleted;

    private String reabBeneAccount;

    private String reabBeneIban;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting reabTranferType;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting reabExpenseType;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting reabVendorSubType;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting reabContractorSubType;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting reabInfrastructureCategory;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting reabSalesCategory;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "rel_realestateassest_beneficiary__realestateassest",
            joinColumns = @JoinColumn(name = "real_estate_assest_beneficiary_id"),
            inverseJoinColumns = @JoinColumn(name = "real_estate_assest_id")
    )
   @JsonIgnore

    private Set<RealEstateAssest> realEstateAssests = new HashSet<>();

   /* @OneToMany(mappedBy = "realEstateAssestBeneficiary")
    @JsonIgnore
    private transient Set<BankAccount> bankAccounts ;
*/
    @OneToMany(mappedBy = "realEstateAssestBeneficiary")
    @JsonIgnore
    private Set<FundEgress> fundEgresses;

    private Boolean enabled ;

    private Boolean deleted;
}
