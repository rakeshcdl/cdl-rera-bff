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
 * Defines the type of unit available to Capital Partners,
 * such as Apartment, Villa, Commercial Office, etc.
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
@Table(name = "capital_partner_unit_type")
public class CapitalPartnerUnitType implements Serializable {
    @Id
    @SequenceGenerator(
            name = "capital_partner_unit_type_id_seq_gen",
            sequenceName = "capital_partner_unit_type_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "capital_partner_unit_type_id_seq_gen"
    )
    private Long id ;

    private String cputName;

    @Lob
    @Column(name = "cput_icon" , columnDefinition = "TEXT")
    private byte[] cputIcon;

    private String cputIconContentType;

    private Boolean cputIsStandalone;

    private String cpUnitTypePrefix;

    private String cputExcelFormula;

    private String cputJsFormula;

    @ManyToOne
    @JsonIgnore
    private CapitalPartnerUnitType capitalPartnerParentUnitType;

    @OneToMany(mappedBy = "capitalPartnerParentUnitType")
    @JsonIgnore
    private Set<CapitalPartnerUnitType> capitalPartnerChildrenUnitType ;

    @OneToMany(mappedBy = "capitalPartnerUnitType")
    @JsonIgnore
    private Set<CapitalPartnerUnit> capitalPartnerUnits;

    private Boolean enabled ;

    private Boolean deleted;
}
