package com.cdl.escrow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * Represents a module within the application.
 * A module typically represents a functional area of the application such as "User Management",
 * "Finance", or "Reports". Each module can have associated features, settings, and functionalities.
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
@Table(name = "application_module")
public class ApplicationModule implements Serializable {

    @Id
    @SequenceGenerator(
            name = "application_module_id_seq_gen",
            sequenceName = "application_module_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "application_module_id_seq_gen"
    )
    private Long id ;

    private String moduleName;

    private String moduleCode;

    private String moduleDescription;

   private Boolean isActive ;

    private boolean deleted ;


}
