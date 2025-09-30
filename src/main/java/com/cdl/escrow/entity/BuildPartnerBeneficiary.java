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
 * Represents the financial beneficiary associated with a Build Partner.
 * Typically used to route escrow disbursements or verify payment recipients.
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
@Table(name = "build_partner_beneficiary")
public class BuildPartnerBeneficiary implements Serializable {
    @Id
    @SequenceGenerator(
            name = "build_partner_beneficiary_id_seq_gen",
            sequenceName = "build_partner_beneficiary_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "build_partner_beneficiary_id_seq_gen"
    )
    private Long id;

    private String bpbBeneficiaryId;

    private String bpbBeneficiaryType;

    private String bpbName;

    private String bpbBankName;

    private String bpbSwiftCode;

    private String bpbRoutingCode;

    private String bpbAccountNumber;

    private Boolean bpbIsActive;

    private Boolean bpbIsDeleted;
    private Boolean enabled ;

    private Boolean deleted;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting bpbTransferType;

    /*@OneToMany(mappedBy = "buildPartnerBeneficiary")
    @JsonIgnore
    private  Set<BankAccount> bpbBankAccounts ;
*/
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "rel_buildpartner_beneficiary__buildpartner",
            joinColumns = @JoinColumn(name = "buildpartner_beneficiary_id"),
            inverseJoinColumns = @JoinColumn(name = "buildpartner_id")
    )
    @JsonIgnore
    private Set<BuildPartner> buildPartners = new HashSet<>();
}
