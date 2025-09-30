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
 * Represents a financial institution or bank in the system.
 * This entity stores information about the bank such as its name, address,
 * contact details, and other related financial information.
 * It serves as the primary entity for managing banking relationships.
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
@Table(name = "financial_institution")
public class FinancialInstitution implements Serializable {
    @Id
    @SequenceGenerator(
            name = "financial_institution_id_seq_gen",
            sequenceName = "financial_institution_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "financial_institution_id_seq_gen"
    )
    private Long id;

    private String fiName;

    private String fiAddress;

    private String fiContactNumber;

    private String fiCode;

    private String fiAccountNumber;

    private Double fiAccountBalance;

    private String fiIbanNo;

    private ZonedDateTime fiOpeningDate;

    private String fiAccountTitle;

    private String fiSwiftCode;

    private String fiRoutingCode;

    private String fiSchemeType;

    @OneToMany(mappedBy = "financialInstitution")
    @JsonIgnore
    private Set<BankBranch> branches;
    private Boolean enabled ;

    private Boolean deleted;

}

