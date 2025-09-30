package com.cdl.escrow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Jacksonized
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "task_status")
public class TaskStatus implements Serializable {

    @Id
    @SequenceGenerator(
            name = "task_status_id_seq_gen",
            sequenceName = "task_status_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_status_id_seq_gen"
    )
    private Long id;

    private String code; // Stable short code, e.g. "PEND", "APPR"

    private String name;

    private String description;

    private ZonedDateTime createdAt = ZonedDateTime.now();

    private ZonedDateTime updatedAt = ZonedDateTime.now();

    private Boolean enabled ;

    private Boolean deleted;
}
