package com.cdl.escrow.workflow.mapper;

import com.cdl.escrow.entity.WorkflowStageTemplate;
import com.cdl.escrow.helper.EntityMapper;
import com.cdl.escrow.workflow.dto.WorkflowStageTemplateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkflowStageTemplateMapper extends EntityMapper<WorkflowStageTemplateDTO, WorkflowStageTemplate> {

    @Mapping(source = "workflowDefinition", target = "workflowDefinitionDTO")
    WorkflowStageTemplateDTO toDto(WorkflowStageTemplate workflowStageTemplate);

    @Mapping(source = "workflowDefinitionDTO", target = "workflowDefinition")
    WorkflowStageTemplate toEntity(WorkflowStageTemplateDTO workflowStageTemplateDTO);
}

