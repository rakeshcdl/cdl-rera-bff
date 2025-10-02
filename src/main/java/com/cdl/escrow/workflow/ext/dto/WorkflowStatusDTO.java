package com.cdl.escrow.workflow.ext.dto;

import lombok.Data;

import java.util.List;

@Data
public class WorkflowStatusDTO {

    private Long requestId;
    private String status;
    private String currentStage;
    private Integer totalStages;
    private Integer completedStages;
    private List<WorkflowStageDTO> stageHistory;
}
