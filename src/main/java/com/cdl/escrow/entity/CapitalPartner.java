package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents an investor or capital partner providing funds for real estate projects.
 * Investors contribute to the escrow account and may receive updates on fund utilization.
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
@Table(name = "capital_partner")
public class CapitalPartner implements Serializable {
    @Id
    @SequenceGenerator(
            name = "capital_partner_id_seq_gen",
            sequenceName = "capital_partner_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "capital_partner_id_seq_gen"
    )
    private Long id ;

    private String capitalPartnerId;

    private String capitalPartnerName;

    private String capitalPartnerMiddleName;

    private String capitalPartnerLastName;

    private Float capitalPartnerOwnershipPercentage;

    private String capitalPartnerIdNo;

    private String capitalPartnerTelephoneNo;

    private String capitalPartnerMobileNo;

    private String capitalPartnerEmail;

    private Integer capitalPartnerOwnerNumber;

    private Boolean isCurrent;

    private ZonedDateTime idExpiaryDate;

    private String capitalPartnerLocaleName;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting documentType;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting countryOption;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting investorType;

    @OneToMany(mappedBy = "capitalPartner")
    @JsonIgnore
    private Set<CapitalPartnerBankInfo> capitalPartnerBankInfos = new HashSet<>();

    @ManyToOne
    @JsonIgnore
    private CapitalPartnerUnit capitalPartnerUnit;

    private Boolean enabled ;

    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    private TaskStatus taskStatus;
}
