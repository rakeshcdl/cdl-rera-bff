package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * Stores the bank information of a Capital Partner,
 * used for financial transactions like refunds or capital returns.
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
@Table(name = "capital_partner_bank_info")
public class CapitalPartnerBankInfo implements Serializable {
    @Id
    @SequenceGenerator(
            name = "capital_partner_bank_info_id_seq_gen",
            sequenceName = "capital_partner_bank_info_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "capital_partner_bank_info_id_seq_gen"
    )
    private Long id ;

    private String cpbiPayeeName;

    private String cpbiPayeeAddress;

    private String cpbiBankName;

    private String cpbiBankAddress;

    private String cpbiBicCode;

    private String cpbiBeneRoutingCode;

    private String cpbiAccountNumber;

    private String cpbiIban;

   /* @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private  BankAccount bankAccount;
*/
    @ManyToOne
    @JsonIgnore
    private CapitalPartner capitalPartner;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting payMode;
    private Boolean enabled ;

    private Boolean deleted;
}
