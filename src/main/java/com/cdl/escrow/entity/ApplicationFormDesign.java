package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.Map;

/**
 * Represents the design of a user interface form in the application.
 * This entity defines the fields, layout, validations, and logic associated with a form
 * in the application, such as a "Create User" or "Submit Request" form.
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
@Table(name = "application_form_design")
public class ApplicationFormDesign implements Serializable {

    @Id
    @SequenceGenerator(
            name = "application_form_design_id_seq_gen",
            sequenceName = "application_form_design_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "application_form_design_id_seq_gen"
    )
    private Long id ;

    private String formName;

    /*@Column(name = "form_definition", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)*/
    private String formDefinition;


    private Boolean enabled ;


    private Boolean deleted ;


}