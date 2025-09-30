package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowStageStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Jacksonized
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "workflow_request_stage")
public class WorkflowRequestStage implements Serializable {

    @Id
    @SequenceGenerator(
            name = "workflow_request_stage_id_seq_gen",
            sequenceName = "workflow_request_stage_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "workflow_request_stage_id_seq_gen"
    )
    private Long id;

    @ManyToOne
    private WorkflowRequest workflowRequest;

    private Integer stageOrder;

    private String stageKey;

    private String keycloakGroup;

    private Integer requiredApprovals;

    private Integer approvalsObtained ;

    private ZonedDateTime startedAt;

    private ZonedDateTime completedAt;

    private Long version;

    @OneToMany(mappedBy = "workflowRequestStage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkflowRequestStageApproval> workflowRequestStageApprovals;

    private Boolean enabled ;

    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    private TaskStatus taskStatus;
}
