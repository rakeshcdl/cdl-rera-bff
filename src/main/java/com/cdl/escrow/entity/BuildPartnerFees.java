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
 * Represents various fees, charges, or levies applied to a Build Partner.
 * This may include registration fees, regulatory costs, or service charges.
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
@Table(name = "build_partner_fees")
public class BuildPartnerFees implements Serializable {
    @Id
    @SequenceGenerator(
            name = "build_partner_fees_id_seq_gen",
            sequenceName = "build_partner_fees_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "build_partner_fees_id_seq_gen"
    )
    private Long id ;

    private Double debitAmount;

    private Double totalAmount;

    private ZonedDateTime feeCollectionDate;

    private ZonedDateTime feeNextRecoveryDate;

    private Double feePercentage;

    private Double vatPercentage;

    private Boolean feeCollected;

    @ManyToOne
    private ApplicationSetting bpFeeCategory;

    @ManyToOne
    private ApplicationSetting bpFeeFrequency;

    @ManyToOne
    private ApplicationSetting bpFeeCurrency;

    @ManyToOne
    private ApplicationSetting bpAccountType;

   /* @ManyToOne
    private BankAccount debitAccount;
*/
    @ManyToOne
    @JsonIgnore
    private BuildPartner buildPartner;

    private Boolean enabled ;

    private Boolean deleted;
}
