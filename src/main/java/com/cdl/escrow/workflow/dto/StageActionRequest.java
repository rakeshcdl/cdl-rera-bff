package com.cdl.escrow.workflow.dto;

import com.cdl.escrow.enumeration.WorkflowDecision;
import lombok.Data;

@Data
public class StageActionRequest {
    private String userId;

    private String remarks;

    private WorkflowDecision decision;

}
