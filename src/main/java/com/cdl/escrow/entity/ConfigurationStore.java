package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * A storage mechanism for key-value pairs representing configuration data.
 * It allows storing and retrieving configuration values that are used throughout the application.
 * These values could include application settings, user preferences, or runtime parameters.
 * The store may interact with a database or in-memory storage system.
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
@Table(name = "configuration_store")
public class ConfigurationStore implements Serializable {
    @Id
    @SequenceGenerator(
            name = "configuration_store_id_seq_gen",
            sequenceName = "configuration_store_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "configuration_store_id_seq_gen"
    )
    private Long id ;

    private String key;

    private String value;
    private Boolean enabled ;

    private Boolean deleted;
}
