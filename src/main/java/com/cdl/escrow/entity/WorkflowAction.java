package com.cdl.escrow.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Jacksonized
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "work_flow_action")
public class WorkflowAction implements Serializable {
    @Id
    @SequenceGenerator(
            name = "work_flow_action_id_seq_gen",
            sequenceName = "work_flow_action_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "work_flow_action_id_seq_gen"
    )
    private Long id;

    //@Column(unique = true, nullable = false, length = 64)
    private String actionKey;

    //@Column(nullable = false, length = 128)
    private String actionName;

    private String description;

    private String moduleCode;

    private String name;

    private Boolean enabled ;

    private Boolean deleted;
}
