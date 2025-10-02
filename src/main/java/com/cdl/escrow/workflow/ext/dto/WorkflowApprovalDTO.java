package com.cdl.escrow.workflow.ext.dto;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class WorkflowApprovalDTO {

    private Long id;
    private String approverUserId;
    private String approverUsername;
    private String approverGroup;
    private String decision;
    private String remarks;
    private ZonedDateTime decidedAt;
}
