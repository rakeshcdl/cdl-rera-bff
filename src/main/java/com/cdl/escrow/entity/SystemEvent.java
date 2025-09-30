package com.cdl.escrow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * Represents an event that occurs within the system.
 * This entity is used to track significant occurrences in the application,
 * such as user actions, system states, or background processes.
 * Events may be logged for monitoring, debugging, or processing by event-driven systems.
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
@Table(name = "system_event")
public class SystemEvent implements Serializable {
    @Id
    @SequenceGenerator(
            name = "system_event_id_seq_gen",
            sequenceName = "system_event_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "system_event_id_seq_gen"
    )
    private Long id ;

    private String eventId;

    private String eventType;

    private String userId;

    private String timestamp;

    private String eventDetails;

    private Boolean enabled ;

    private Boolean deleted;
}