package com.cdl.escrow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * Represents a resource used by the application, such as files, database records, or external services.
 * This entity tracks information related to the resource, such as resource ID, type, and access permissions.
 * Resources are central to managing how data or services are utilized within the system.
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
@Table(name = "system_resource")
public class SystemResource implements Serializable {
    @Id
    @SequenceGenerator(
            name = "system_resource_id_seq_gen",
            sequenceName = "system_resource_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "system_resource_id_seq_gen"
    )
    private Long id ;

    private String resourceId;

    private String resourceType;

    private String accessLevel;
    private Boolean enabled ;

    private Boolean deleted;
}
