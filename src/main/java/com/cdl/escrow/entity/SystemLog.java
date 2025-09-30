package com.cdl.escrow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * Represents a log entry for system activities, errors, or informational messages.
 * This entity stores log data such as log level (e.g., INFO, ERROR), timestamp, and message content.
 * System logs are essential for debugging, monitoring, and auditing system behavior.
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
@Table(name = "system_log")
public class SystemLog implements Serializable {
    @Id
    @SequenceGenerator(
            name = "system_log_id_seq_gen",
            sequenceName = "system_log_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "system_log_id_seq_gen"
    )
    private Long id ;

    private String logId;

    private String logLevel;

    private String message;

    private String timestamp;
    private Boolean enabled ;

    private Boolean deleted;
}
