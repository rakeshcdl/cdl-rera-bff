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
 * Represents the financial or escrow account assigned to a Build Partner.
 * Used for receiving disbursements and tracking fund flows.
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
@Table(name = "build_partner_account")
public class BuildPartnerAccount implements Serializable {
    @Id
    @SequenceGenerator(
            name = "build_partner_account_id_seq_gen",
            sequenceName = "build_partner_account_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "build_partner_account_id_seq_gen"
    )
    private Long id ;


    @Column(nullable = false, length = 50)
    private String accountType; // TRUST, RETENTION, SUB_CONSTRUCTION, CORPORATE

    @Column(nullable = false, unique = true, length = 50)
    private String accountNumber;

    @Column(nullable = false, unique = true, length = 50)
    private String ibanNumber;

    @Column(nullable = false)
    private ZonedDateTime dateOpened;

    @Column(length = 255)
    private String accountTitle;

    @Column(nullable = false, length = 10)
    private String currencyCode;

    private Boolean isValidated = Boolean.FALSE;
    private Boolean enabled ;

    private Boolean deleted;
    @Column(nullable = false, updatable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();

    private ZonedDateTime updatedAt = ZonedDateTime.now();


    @ManyToOne
    @JsonIgnore
    private BuildPartner buildPartner;
}
