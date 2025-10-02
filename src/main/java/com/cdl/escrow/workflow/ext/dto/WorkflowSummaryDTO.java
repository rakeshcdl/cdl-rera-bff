package com.cdl.escrow.workflow.ext.dto;

import lombok.Data;

import java.util.Map;

@Data
public class WorkflowSummaryDTO {
    private Integer totalAwaitingActions;
    private Integer totalEngagements;
    private Map<String, Integer> awaitingActionsByModule;
    private Map<String, Integer> engagementsByModule;
    private Map<String, Integer> awaitingActionsByStage;
}
