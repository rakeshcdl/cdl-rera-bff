package com.cdl.escrow.workflow.ext.dto;

import lombok.Data;

import java.util.List;

@Data
public class BulkWorkflowDecisionDTO {

    private List<StageDecisionDTO> decisions;
}
