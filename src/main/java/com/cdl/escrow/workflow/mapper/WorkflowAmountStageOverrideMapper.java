package com.cdl.escrow.workflow.mapper;

import com.cdl.escrow.entity.WorkflowAmountStageOverride;
import com.cdl.escrow.helper.EntityMapper;
import com.cdl.escrow.workflow.dto.WorkflowAmountStageOverrideDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkflowAmountStageOverrideMapper extends EntityMapper<WorkflowAmountStageOverrideDTO, WorkflowAmountStageOverride> {

    @Mapping(source = "workflowAmountRule", target = "workflowAmountRuleDTO")
    WorkflowAmountStageOverrideDTO toDto(WorkflowAmountStageOverride workflowAmountStageOverride);

    @Mapping(source = "workflowAmountRuleDTO", target = "workflowAmountRule")
    WorkflowAmountStageOverride toEntity(WorkflowAmountStageOverrideDTO workflowAmountStageOverrideDTO);
}
