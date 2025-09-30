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
 * Represents the bank account linked to a Real Estate Asset,
 * used for escrow disbursements or project-related financial operations.
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
@Table(name = "real_estate_assest_bank_account")
public class RealEstateBankAccount implements Serializable {
    @Id
    @SequenceGenerator(
            name = "real_estate_assest_bank_account_id_seq_gen",
            sequenceName = "real_estate_assest_bank_account_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "real_estate_assest_bank_account_id_seq_gen"
    )
    private Long id ;


    private String accountType; // TRUST, RETENTION, SUB_CONSTRUCTION, CORPORATE


    private String accountNumber;


    private String ibanNumber;


    private ZonedDateTime dateOpened;


    private String accountTitle;


    private String currencyCode;

    private Boolean isValidated = Boolean.FALSE;


    private ZonedDateTime createdAt = ZonedDateTime.now();

    private ZonedDateTime updatedAt = ZonedDateTime.now();


    @ManyToOne
    @JsonIgnore
    private RealEstateAssest realEstateAssest;

    private Boolean enabled ;

    private Boolean deleted;

}
