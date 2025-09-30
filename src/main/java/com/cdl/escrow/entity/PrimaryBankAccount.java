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
 * Represents a master or primary bank account in the system.
 * This account is typically used for holding central funds or conducting key financial operations.
 * It stores details about the account, such as the account number, bank information, and balance.
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
@Table(name = "primary_bank_account")
public class PrimaryBankAccount implements Serializable {
    @Id
    @SequenceGenerator(
            name = "primary_bank_account_id_seq_gen",
            sequenceName = "primary_bank_account_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_bank_account_id_seq_gen"
    )
    private Long id ;

    private String pcAccountNumber;

    private double pbBalance;

    private String pbLabelId;

    private String pbTypeId;


    @OneToMany(mappedBy = "primaryBankAccount")
    @JsonIgnore
    private Set<SecondaryBankAccount> secondaryBankAccounts ;


    @OneToMany(mappedBy = "primaryBankAccount")
    @JsonIgnore
    private transient Set<BankAccount> bankAccounts ;
    private Boolean enabled ;

    private Boolean deleted;
}
