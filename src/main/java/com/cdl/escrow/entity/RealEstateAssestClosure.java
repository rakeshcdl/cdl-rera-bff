package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * Represents the closure phase of a Real Estate Asset,
 * capturing final approvals, audits, and financial settlements upon project completion.
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
@Table(name = "real_estate_assest_closure")
public class RealEstateAssestClosure implements Serializable {
    @Id
    @SequenceGenerator(
            name = "real_estate_assest_closure_id_seq_gen",
            sequenceName = "real_estate_assest_closure_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "real_estate_assest_closure_id_seq_gen"
    )
    private Long id;

    private Double reacTotalIncomeFund;

    private Double reacTotalPayment;

    private Double reacCheckGuranteeDoc;

    @ManyToOne
    @JsonIgnore
    private RealEstateAssest realEstateAssest;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting reacDocument;

    private Boolean enabled ;

    private Boolean deleted;

}
