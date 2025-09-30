package com.cdl.escrow.workflow.dto;

import com.cdl.escrow.dto.TaskStatusDTO;
import com.cdl.escrow.entity.TaskStatus;
import com.cdl.escrow.enumeration.WorkflowDecision;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowRequestStageApprovalDTO implements Serializable {

    private Long id;

    private String approverUserId;

    private String approverUsername;

    private String approverGroup;

    private String remarks;

    private ZonedDateTime decidedAt;

    private WorkflowRequestStageDTO workflowRequestStageDTO;

    private TaskStatusDTO taskStatusDTO;
}
