package com.cdl.escrow.workflow.dto;

import com.cdl.escrow.dto.TaskStatusDTO;
import com.cdl.escrow.entity.TaskStatus;
import com.cdl.escrow.entity.WorkflowRequest;
import com.cdl.escrow.enumeration.WorkflowStageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowRequestStageDTO implements Serializable {

    private Long id;


    private Integer stageOrder;

    private String stageKey;

    private String keycloakGroup;

    private int requiredApprovals;

    private int approvalsObtained ;

    private ZonedDateTime startedAt;

    private ZonedDateTime completedAt;

    private Long version;

    private WorkflowRequestDTO workflowRequestDTO;

   private List<WorkflowRequestStageApprovalDTO> workflowRequestStageApprovalDTOS;

    private TaskStatusDTO taskStatusDTO;
}
