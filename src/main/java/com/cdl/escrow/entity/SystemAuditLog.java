package com.cdl.escrow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * Represents an audit log entry for tracking system events.
 * This entity records important actions, changes, and events within the system,
 * such as user activity, system errors, or administrative actions.
 * It is essential for compliance, security, and troubleshooting purposes.
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
@Table(name = "system_audit_log")
public class SystemAuditLog implements Serializable {
    @Id
    @SequenceGenerator(
            name = "system_audit_log_id_seq_gen",
            sequenceName = "system_audit_log_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "system_audit_log_id_seq_gen"
    )
    private Long id ;

    private String eventId;

    private String eventType;

    private String userId;

    private String timestamp;

    private String details;

    private Boolean enabled ;

    private Boolean deleted;

}
