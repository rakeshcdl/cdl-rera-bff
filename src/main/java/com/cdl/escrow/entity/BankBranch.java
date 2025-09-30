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
 * Represents a branch of a bank or financial institution.
 * This entity holds information about a specific branch, including its location,
 * contact details, and associated financial institution.
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
@Table(name = "bank_branch")
public class BankBranch implements Serializable {
    @Id
    @SequenceGenerator(
            name = "bank_branch_id_seq_gen",
            sequenceName = "bank_branch_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bank_branch_id_seq_gen"
    )
    private Long id ;

    private String bankBranchName;

    private String bankBranchAddress;

    private String bankBranchCode;

    private String bankBranchIfscCode;

    private String bankBranchSwiftCode;

    private String bankBranchRoutingCode;

    private String bankBranchTtcCode;

    private Boolean enabled ;

    private Boolean deleted;

    @ManyToOne
    @JsonIgnore
    private FinancialInstitution financialInstitution;

    @OneToMany(mappedBy = "bankBranch")
    private transient Set<BankAccount> bankAccounts;

}
