package com.cdl.escrow.workflow.ext.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;

@Data
public class WorkflowEngagementDTO {
    private Long requestId;
    private Long stageId;
    private String referenceId;
    private String referenceType;
    private String moduleName;
    private String actionKey;
    private BigDecimal amount;
    private String currency;
    private Map<String, Object> payloadJson;

    private Integer stageOrder;
    private String stageKey;
    private String stageName;
    private String stageStatus;

    private String myDecision;
    private String myRemarks;
    private ZonedDateTime myActionDate;

    private String requestStatus;
    private Integer currentStageOrder;
    private String createdBy;
    private ZonedDateTime createdAt;
}
