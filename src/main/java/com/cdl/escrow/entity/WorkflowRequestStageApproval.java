package com.cdl.escrow.entity;

import com.cdl.escrow.enumeration.WorkflowDecision;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Jacksonized
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "workflow_request_stage_approval")
public class WorkflowRequestStageApproval implements Serializable {

    @Id
    @SequenceGenerator(
            name = "workflow_request_stage_approval_id_seq_gen",
            sequenceName = "workflow_request_stage_approval_id_seq",
            allocationSize = 50
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "workflow_request_stage_approval_id_seq_gen"
    )
    private Long id;

    @ManyToOne
    private WorkflowRequestStage workflowRequestStage;

    private String approverUserId;

    private String approverUsername;

    private String approverGroup;

    private String remarks;

    @CreationTimestamp
    private ZonedDateTime decidedAt;

    private Boolean enabled ;

    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY)
    private TaskStatus taskStatus;
}
