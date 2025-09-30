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
@Table(name = "workflow_amount_stage_override")
public class WorkflowAmountStageOverride implements Serializable {

    @Id
    @SequenceGenerator(
            name = "workflow_amount_stage_override_id_seq_gen",
            sequenceName = "workflow_amount_stage_override_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "workflow_amount_stage_override_id_seq_gen"
    )
    private Long id;

    @ManyToOne
    private WorkflowAmountRule workflowAmountRule;


    private Integer stageOrder;


    private String stageKey;

    private Integer requiredApprovals;

    private String keycloakGroup;

    private Boolean enabled ;

    private Boolean deleted;
}
