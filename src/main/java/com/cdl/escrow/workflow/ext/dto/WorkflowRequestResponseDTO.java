package com.cdl.escrow.workflow.ext.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@Data
public class WorkflowRequestResponseDTO {
    private Long id;
    private String referenceId;
    private String referenceType;
    private String moduleName;
    private String actionKey;
    private BigDecimal amount;
    private String currency;
    private Map<String, Object> payloadJson;
    private String status;
    private Integer currentStageOrder;
    private String createdBy;
    private ZonedDateTime createdAt;
    private ZonedDateTime lastUpdatedAt;
    private List<WorkflowStageDTO> stages;
    private WorkflowStageDTO currentStage;
}
