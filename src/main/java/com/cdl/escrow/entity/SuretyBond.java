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
 * Represents a financial guarantee, such as a bank guarantee or surety bond,
 * provided by the developer to ensure project delivery and financial compliance.
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
@Table(name = "surety_bond")
public class SuretyBond implements Serializable {
    @Id
    @SequenceGenerator(
            name = "surety_bond_id_seq_gen",
            sequenceName = "surety_bond_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "surety_bond_id_seq_gen"
    )
    private Long id ;

    private String suretyBondReferenceNumber;

    private ZonedDateTime suretyBondDate;

    private String suretyBondName;

    private Boolean suretyBondOpenEnded;

    private ZonedDateTime suretyBondExpirationDate;

    private Double suretyBondAmount;

    private ZonedDateTime suretyBondProjectCompletionDate;

    private String suretyBondNoOfAmendment;

    private String suretyBondContractor;

    private String suretyBondNewReadingAmendment;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting suretyBondType;

    @ManyToOne
    @JsonIgnore
    private RealEstateAssest realEstateAssest;

    @ManyToOne
    @JsonIgnore
    private FinancialInstitution issuerBank;

    /*@ManyToOne
    @JsonIgnore
    private transient BankAccount accountNumber;
*/
    @ManyToOne
    @JsonIgnore
    private ApplicationSetting suretyBondStatus;

    @OneToMany(mappedBy = "suretyBond")
    @JsonIgnore
    private Set<SuretyBondRecovery> suretyBondRecoveries;

    @OneToMany(mappedBy = "suretyBond")
    @JsonIgnore
    private Set<SuretyBondRelease> suretyBondReleases;

    private Boolean enabled ;

    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    private TaskStatus taskStatus;

}
