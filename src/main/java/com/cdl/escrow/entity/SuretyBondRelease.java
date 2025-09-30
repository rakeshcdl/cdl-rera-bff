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
 * Represents the official release or closure of a Security Bond,
 * typically upon successful completion of project milestones or compliance validation.
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
@Table(name = "surety_bond_release")
public class SuretyBondRelease implements Serializable {
    @Id
    @SequenceGenerator(
            name = "surety_bond_release_id_seq_gen",
            sequenceName = "surety_bond_release_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "surety_bond_release_id_seq_gen"
    )
    private Long id ;


    private ZonedDateTime suretyBondReleaseRequestDate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private SuretyBond suretyBond;
    private Boolean enabled ;

    private Boolean deleted;
}
