package com.cdl.escrow.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyEngagementDTO implements Serializable {

    Long requestId;
    String referenceId;
    String moduleName;
    String actionKey;
    String requestStatus;    // taskStatus.code of WorkflowRequest
    Integer currentStageOrder;
    String currentStageKey;
    String currentStageStatus;// taskStatus.code of current stage
    String userRole;        // CREATOR or APPROVER
    java.time.ZonedDateTime lastActionAt;
    Map<String, Object> payloadJson;
}
