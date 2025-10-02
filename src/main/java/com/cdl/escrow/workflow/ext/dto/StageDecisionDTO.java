package com.cdl.escrow.workflow.ext.dto;

import lombok.Data;

@Data
public class StageDecisionDTO {
    private Long stageId;
    private String decision;
    private String remarks;
}
