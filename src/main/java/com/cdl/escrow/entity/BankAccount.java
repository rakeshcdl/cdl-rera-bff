/**
 * BankAccount.java
 *
 * @author Rakesh Raushan
 * @version 1.0
 * @description Escrow RERA application
 * @since 15/07/25
 */


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

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Jacksonized
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "bank_account")
public class BankAccount implements Serializable {

    @Id
    @SequenceGenerator(
            name = "bank_account_id_seq_gen",
            sequenceName = "bank_account_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bank_account_id_seq_gen"
    )
    private Long id;

    private String bankAccountNumber;

    private Double bankAccountBalance;

    private String bankAccountIbanNo;

    private ZonedDateTime bankAccountOpenedDate;

    private String bankAccountTitle;

    private String bankAccountSwiftcode;

    private String bankAccountRoutingCode;

    private String bankAccountSchemeType;

    private Boolean deleted ;


    private Boolean enabled ;

    /*@ManyToOne
    @JsonIgnore
    private BankBranch bankBranch;

    @ManyToOne
    private FinancialInstitution financialInstitution;

    @ManyToOne
    @JsonIgnore
    private ApplicationSetting bankCurrency;

    @ManyToOne
    @JsonIgnore
    private PrimaryBankAccount primaryBankAccount;

    @ManyToOne
    @JsonIgnore
    private SecondaryBankAccount secondaryBankAccount;*/

   /* @ManyToOne
    @JsonIgnore
    private BuildPartnerBeneficiary buildPartnerBeneficiary;
*/
   /* @ManyToOne
    @JsonIgnore
    private RealEstateAssest realEstateAssest;
*/
  /*  @ManyToOne
    @JsonIgnore
    private BuildPartner buildPartner;
*/
    /*@ManyToOne
    @JsonIgnore
    private RealEstateAssestBeneficiary realEstateAssestBeneficiary;
*/
   /* @OneToMany(mappedBy = "bankAccount")
    @JsonIgnore
    private Set<CapitalPartnerBankInfo> capitalPartnerBankInfos;
*/
  /*  @ManyToOne
    @JsonIgnore
    private CapitalPartnerUnit capitalPartnerUnit;
*/

}
