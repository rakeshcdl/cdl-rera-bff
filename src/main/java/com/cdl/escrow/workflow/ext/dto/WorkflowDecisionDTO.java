package com.cdl.escrow.workflow.ext.dto;

import lombok.Data;

@Data
public class WorkflowDecisionDTO {

    private String userId;
    private String username;
    private String decision; // APPROVE or REJECT
    private String remarks;
    private String userGroup;
}
