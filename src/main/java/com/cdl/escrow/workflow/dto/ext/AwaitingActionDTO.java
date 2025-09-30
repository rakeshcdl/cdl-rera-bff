package com.cdl.escrow.workflow.dto.ext;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class AwaitingActionDTO {

    Long requestId;
    String referenceId;
    String moduleName;
    String actionKey;
    String createdBy;
    java.time.ZonedDateTime createdAt;

    Long stageId;
    Integer stageOrder;
    String stageKey;
    String keycloakGroup;
    Integer requiredApprovals;
    Integer approvalsObtained;
    Integer approvalsRemaining;

    Map<String, Object> payloadJson;
}
