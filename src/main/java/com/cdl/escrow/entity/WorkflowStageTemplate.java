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
@Table(name = "workflow_stage_template")
public class WorkflowStageTemplate implements Serializable {

    @Id
    @SequenceGenerator(
            name = "workflow_stage_template_id_seq_gen",
            sequenceName = "workflow_stage_template_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "workflow_stage_template_id_seq_gen"
    )
    private Long id;

    @ManyToOne
    private WorkflowDefinition workflowDefinition;

    private Integer stageOrder;

    private String stageKey;

    private String keycloakGroup;

    private Integer requiredApprovals ;

    private String name;

    private String description;

    private Integer slaHours;

    private Boolean enabled ;

    private Boolean deleted;
}
