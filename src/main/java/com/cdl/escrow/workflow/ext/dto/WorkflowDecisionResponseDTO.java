package com.cdl.escrow.workflow.ext.dto;

import lombok.Data;

@Data
public class WorkflowDecisionResponseDTO {
    private Long requestId;
    private Long stageId;
    private String message;
    private String workflowStatus;
    private Integer currentStageOrder;
    private String nextStage;
}
