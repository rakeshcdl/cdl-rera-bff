package com.cdl.escrow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Jacksonized
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "capital_partner_payment_plan")
public class CapitalPartnerPaymentPlan implements Serializable {

    @Id
    @SequenceGenerator(
            name = "capital_partner_payment_plan_id_seq_gen",
            sequenceName = "capital_partner_payment_plan_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "capital_partner_payment_plant_id_seq_gen"
    )
    private Long id;

    private Integer cpppInstallmentNumber;

    private ZonedDateTime cpppInstallmentDate;

    private Double cpppBookingAmount;

    // Many installments belong to one capital Partner
    @ManyToOne(fetch = FetchType.LAZY)
    private CapitalPartner capitalPartner;
    private Boolean enabled ;

    private Boolean deleted;
}
