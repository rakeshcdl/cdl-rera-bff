package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * Represents the design or schema of a database table in the application.
 * This entity defines the structure of a table, including the table name, column names,
 * column types, and any constraints or relationships with other tables.
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
@Table(name = "application_table_design")
public class ApplicationTableDesign implements Serializable {

    @Id
    @SequenceGenerator(
            name = "application_table_design_id_seq_gen",
            sequenceName = "application_table_design_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "application_table_design_id_seq_gen"
    )
    private Long id ;

    private String tableName;

    @Lob
    @Column(name = "table_definition", columnDefinition = "TEXT")
    private String tableDefinition;

    @Enumerated(EnumType.STRING)
    private WorkflowStatus status;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean enabled = true;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted = false; // default false

}
