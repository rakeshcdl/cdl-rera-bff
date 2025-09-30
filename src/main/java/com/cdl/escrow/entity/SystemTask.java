package com.cdl.escrow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

/**
 * Represents a task or job that is scheduled or executed within the application.
 * This entity tracks task status, priority, execution time, and any related metadata.
 * It can be used to manage background processing, scheduled tasks, or system jobs.
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
@Table(name = "system_task")
public class SystemTask implements Serializable {
    @Id
    @SequenceGenerator(
            name = "system_task_id_seq_gen",
            sequenceName = "system_task_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "system_task_id_seq_gen"
    )
    private Long id ;

    private String taskId;

    private String taskName;

    private String taskStatus;

    private long scheduledAt;

    private long completedAt;

    private Boolean enabled ;

    private Boolean deleted;
}
