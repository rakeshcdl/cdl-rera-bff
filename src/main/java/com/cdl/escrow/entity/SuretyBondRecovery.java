package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * Represents the process of recovering funds from a Security Bond,
 * typically due to non-compliance, delay, or breach of agreement.
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
@Table(name = "surety_bond_recovery")
public class SuretyBondRecovery implements Serializable {
    @Id
    @SequenceGenerator(
            name = "surety_bond_recovery_id_seq_gen",
            sequenceName = "surety_bond_recovery_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "surety_bond_recovery_id_seq_gen"
    )
    private Long id ;

    private Double suretyBondRecoveryReductionAmount;

    private Double suretyBondRecoveryBalanceAmount;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnore
    private SuretyBond suretyBond;
    private Boolean enabled ;

    private Boolean deleted;

}
