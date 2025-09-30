package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Set;

/**
 * Represents the booking or reservation of a unit by a Capital Partner.
 * Precedes full purchase and may include booking amount and validity.
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
@Table(name = "capital_partner_unit_booking")
public class CapitalPartnerUnitBooking implements Serializable {
    @Id
    @SequenceGenerator(
            name = "capital_partner_unit_booking_id_seq_gen",
            sequenceName = "capital_partner_unit_booking_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "capital_partner_unit_booking_id_seq_gen"
    )
    private Long id ;

    private Double cpubAmountPaid;

    private Double cpubAreaSize;

    private Double cpubForFeitAmount;

    private Double cpubDldAmount;

    private Double cpubRefundAmount;

    @Lob
    @Column(name = "cupb_remarks" , columnDefinition = "TEXT")
    private String cpubRemarks;

    private Double cpubTransferredAmount;

    @OneToMany(mappedBy = "capitalPartnerUnitBooking")
    @JsonIgnore
    private Set<CapitalPartnerUnit> capitalPartnerUnits ;
    private Boolean enabled ;

    private Boolean deleted;
}
