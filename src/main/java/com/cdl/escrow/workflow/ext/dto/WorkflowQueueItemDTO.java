package com.cdl.escrow.workflow.ext.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;

@Data
public class WorkflowQueueItemDTO {

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
    private Integer requiredApprovals;
    private Integer approvalsObtained;
    private ZonedDateTime startedAt;
    private ZonedDateTime createdAt;
    private String createdBy;
    private Integer pendingApprovals;
}
