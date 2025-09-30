package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * Defines the type of security bond, such as Bank Guarantee,
 * Insurance Guarantee, or Surety Bond.
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
@Table(name = "surety_bond_type")
public class SuretyBondType implements Serializable {
    @Id
    @SequenceGenerator(
            name = "surety_bond_type_id_seq_gen",
            sequenceName = "surety_bond_type_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "surety_bond_type_id_seq_gen"
    )
    private Long id ;

    private String suretyBondTypeLabelId;

    private String suretyBondTypeId;

    private String suretyBondTypeFetchUrl;

    private Boolean enabled ;

    private Boolean deleted;
}
