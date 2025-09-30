package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
 * Represents a real estate developer registered with CDL's RERA Escrow Management System.
 * A developer is responsible for initiating and managing real estate projects.
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
@Table(name = "build_partner")
public class BuildPartner implements Serializable {
    @Id
    @SequenceGenerator(
            name = "build_partner_id_seq_gen",
            sequenceName = "build_partner_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "build_partner_id_seq_gen"
    )
    private Long id;

    private String bpDeveloperId;

    private String bpCifrera;

    private String bpDeveloperRegNo;

    private String bpName;

    private String bpMasterName;

    @Nationalized
    private String bpNameLocal;

    private ZonedDateTime bpOnboardingDate;

    private String bpContactAddress;

    private String bpContactTel;

    private String bpPoBox;

    private String bpMobile;

    private String bpFax;

    private String bpEmail;

    private String bpLicenseNo;

    private ZonedDateTime bpLicenseExpDate;

    private String bpWorldCheckFlag;

    private String bpWorldCheckRemarks;

    private Boolean bpMigratedData;
    private Boolean enabled ;

    private Boolean deleted;

    @Lob
    @Column(name = "bp_remark", columnDefinition = "TEXT")
    private String bpremark;

    @ManyToOne
    private ApplicationSetting bpRegulator;

    @ManyToOne
    private ApplicationSetting bpActiveStatus;

    @ManyToMany(mappedBy = "buildPartners", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<BuildPartnerBeneficiary> buildPartnerBeneficiaries = new HashSet<>();

    @OneToMany(mappedBy = "buildPartner")
    private Set<BuildPartnerContact> buildPartnerContacts;

    @OneToMany(mappedBy = "buildPartner")
    @JsonIgnore
    private Set<RealEstateAssestFinancialSummary> realEstateAssestFinancialSummaries ;

    @OneToMany(mappedBy = "buildPartner")
    @JsonIgnore
    private Set<FundEgress> fundEgresses;
/*

    @OneToMany(mappedBy = "buildPartner")
    @JsonIgnore
    private transient Set<BankAccount> bankAccounts ;
*/

    @OneToMany(mappedBy = "buildPartner")
    private Set<BuildPartnerFees> buildPartnerFees;

    @OneToMany(mappedBy = "buildPartner")
    private Set<BuildPartnerAccount> buildPartnerAccounts;

    @ManyToOne(fetch = FetchType.LAZY)
    private TaskStatus taskStatus;
}
