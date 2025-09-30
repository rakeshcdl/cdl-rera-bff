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
 * Represents a child or secondary bank account linked to a master bank account.
 * This account is typically used for sub-accounting purposes or for managing specific fund allocations.
 * It stores details like account number, associated bank, and balance.
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
@Table(name = "secondary_bank_account")
public class SecondaryBankAccount implements Serializable {
    @Id
    @SequenceGenerator(
            name = "secondary_bank_account_id_seq_gen",
            sequenceName = "secondary_bank_account_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "secondary_bank_account_id_seq_gen"
    )
    private Long id ;

    private String sbAccountNumber;

    private Double sbBalance;

    private String sbLabelId;

    private String sbTypeId;

    @ManyToOne
    @JsonIgnore
    private PrimaryBankAccount primaryBankAccount;

    @OneToMany(mappedBy = "secondaryBankAccount")
    @JsonIgnore
    private transient Set<BankAccount> bankAccounts ;
    private Boolean enabled ;

    private Boolean deleted;
}
