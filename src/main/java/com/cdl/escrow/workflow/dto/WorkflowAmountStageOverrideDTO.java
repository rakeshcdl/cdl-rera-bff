package com.cdl.escrow.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowAmountStageOverrideDTO implements Serializable {

    private Long id;

    private Integer stageOrder;

    private Integer requiredApprovals;

    private String keycloakGroup;

    private String stageKey;

    private WorkflowAmountRuleDTO workflowAmountRuleDTO;
}
