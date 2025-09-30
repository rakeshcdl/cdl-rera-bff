package com.cdl.escrow.workflow.dto;



import com.cdl.escrow.entity.WorkflowAmountStageOverride;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowAmountRuleDTO implements Serializable {

    private Long id;

    private String currency;

    private Double minAmount;

    private Double maxAmount;

    private Integer priority;

    private boolean isActive;

    private Integer requiredMakers;

    private Integer requiredCheckers;

    private WorkflowDefinitionDTO workflowDefinitionDTO;

    private Long workflowId;

    private String amountRuleName;

   private List<WorkflowAmountStageOverride> workflowAmountStageOverrideDTOS;

    private Boolean enabled ;

    private Boolean deleted;
}
