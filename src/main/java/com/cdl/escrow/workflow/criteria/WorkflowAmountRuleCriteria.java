package com.cdl.escrow.workflow.criteria;

import com.cdl.escrow.entity.WorkflowAmountStageOverride;
import com.cdl.escrow.filter.*;
import com.cdl.escrow.workflow.dto.WorkflowDefinitionDTO;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class WorkflowAmountRuleCriteria implements Serializable {

    private LongFilter id;

    private StringFilter currency;

    private DoubleFilter minAmount;

    private DoubleFilter maxAmount;

    private IntegerFilter priority;

    private BooleanFilter isActive;

    private IntegerFilter requiredMakers;

    private IntegerFilter requiredCheckers;

    private LongFilter workflowDefinitionId;

    private LongFilter workflowId;

    private StringFilter amountRuleName;

    private BooleanFilter enabled ;

    private BooleanFilter deleted;
}
