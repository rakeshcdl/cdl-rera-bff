package com.cdl.escrow.workflow.criteria;

import com.cdl.escrow.filter.BooleanFilter;
import com.cdl.escrow.filter.IntegerFilter;
import com.cdl.escrow.filter.LongFilter;
import com.cdl.escrow.filter.StringFilter;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class WorkflowStageTemplateCriteria implements Serializable {

    private LongFilter id;

    private IntegerFilter stageOrder;

    private StringFilter stageKey;

    private StringFilter keycloakGroup;

    private IntegerFilter requiredApprovals ;

    private StringFilter name;

    private StringFilter description;

    private IntegerFilter slaHours;

    private LongFilter workflowDefinitionId;

    private BooleanFilter enabled ;

    private BooleanFilter deleted;
}
