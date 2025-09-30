package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowRequestStatus;
import com.cdl.escrow.enumeration.WorkflowStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * Represents the contact details for a Build Partner (developer),
 * including email, phone, and registered business address.
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
@Table(name = "build_partner_contact")
public class BuildPartnerContact implements Serializable {
    @Id
    @SequenceGenerator(
            name = "build_partner_contact_id_seq_gen",
            sequenceName = "build_partner_contact_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "build_partner_contact_id_seq_gen"
    )
    private Long id;

    private String bpcContactName;

    private String bpcFirstName;

    private String bpcLastName;

    private String bpcContactTelCode;

    private String bpcContactTelNo;

    private String bpcCountryMobCode;

    private String bpcContactMobNo;

    private String bpcContactEmail;

    private String bpcContactAddress;

    private String bpcContactAddressLine1;

    private String bpcContactAddressLine2;

    private String bpcContactPoBox;

    private String bpcContactFaxNo;

    @ManyToOne
    @JsonIgnore
    private BuildPartner buildPartner;

    private Boolean enabled ;

    private Boolean deleted;
}
