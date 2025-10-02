package com.cdl.escrow.workflow.ext.dto;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class WorkflowStageDTO {
    private Long id;
    private Integer stageOrder;
    private String stageKey;
    private String keycloakGroup;
    private Integer requiredApprovals;
    private Integer approvalsObtained;
    private String status;
    private ZonedDateTime startedAt;
    private ZonedDateTime completedAt;
    private List<WorkflowApprovalDTO> approvals;
}
