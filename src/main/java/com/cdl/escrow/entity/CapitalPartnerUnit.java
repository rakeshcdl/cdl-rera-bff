package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * Represents a real estate unit associated with a Capital Partner.
 * Includes details like unit number, area, and project association.
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
@Table(name = "capital_partner_unit")
public class CapitalPartnerUnit implements Serializable {
    @Id
    @SequenceGenerator(
            name = "capital_partner_unit_id_seq_gen",
            sequenceName = "capital_partner_unit_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "capital_partner_unit_id_seq_gen"
    )
    private Long id ;

    private String unitRefId;

    private String altUnitRefId;

    private String name;

    private Boolean isResale;

    private ZonedDateTime resaleDate;

    private String unitSysId;

    private String otherFormatUnitNo;

    private String virtualAccNo;

    private String towerName;

    private String unitPlotSize;

    private String floor;

    private String noofBedroom;

    private Boolean isModified;

    @ManyToOne
    @JsonIgnore
    private CapitalPartnerUnit partnerUnit;

    @ManyToOne
    @JsonIgnore
    private CapitalPartnerUnitType capitalPartnerUnitType;

    @ManyToOne
    @JsonIgnore
    private RealEstateAssest realEstateAssest;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting unitStatus;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting propertyId;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting paymentPlanType;

    @ManyToOne
    @JsonIgnore
    private CapitalPartnerUnitBooking capitalPartnerUnitBooking;

    @OneToMany(mappedBy = "capitalPartnerUnit")
    @JsonIgnore
    private Set<RealEstateAssestFeeHistory> projectFeeHistories ;

    @OneToMany(mappedBy = "partnerUnit")
    @JsonIgnore
    private Set<CapitalPartnerUnit> childCapitalPartnerUnit ;

   /* @OneToMany(mappedBy = "capitalPartnerUnit")
    @JsonIgnore
    private transient Set<BankAccount> bankAccounts ;
*/
    @OneToMany(mappedBy = "capitalPartnerUnit")
    @JsonIgnore
    private Set<CapitalPartner> capitalPartners ;

    @OneToMany(mappedBy = "capitalPartnerUnit")
    @JsonIgnore
    private Set<CapitalPartnerUnitPurchase> capitalPartnerUnitPurchases ;

    @OneToMany(mappedBy = "capitalPartnerUnit")
    @JsonIgnore
    private Set<ProcessedFundIngress> processedFundIngresses;

    @OneToMany(mappedBy = "capitalPartnerUnit")
    @JsonIgnore
    private transient Set<PendingFundIngress> pendingFundIngresses;

    @OneToMany(mappedBy = "capitalPartnerUnit")
    @JsonIgnore
    private Set<FundEgress> fundEgresses;

    @OneToMany(mappedBy = "transferCapitalPartnerUnit")
    @JsonIgnore
    private Set<FundEgress> transferFundEgresses;
    private Boolean enabled ;

    private Boolean deleted;

}
