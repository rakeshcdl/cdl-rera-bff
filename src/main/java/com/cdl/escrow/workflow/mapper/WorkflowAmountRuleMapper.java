package com.cdl.escrow.workflow.mapper;

import com.cdl.escrow.entity.WorkflowAmountRule;
import com.cdl.escrow.helper.EntityMapper;
import com.cdl.escrow.workflow.dto.WorkflowAmountRuleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkflowAmountRuleMapper extends EntityMapper<WorkflowAmountRuleDTO, WorkflowAmountRule> {

    @Mapping(source = "workflowDefinition", target = "workflowDefinitionDTO")
    WorkflowAmountRuleDTO toDto(WorkflowAmountRule workflowAmountRule);

    @Mapping(source = "workflowDefinitionDTO", target = "workflowDefinition")
    WorkflowAmountRule toEntity(WorkflowAmountRuleDTO workflowAmountRuleDTO);
}
