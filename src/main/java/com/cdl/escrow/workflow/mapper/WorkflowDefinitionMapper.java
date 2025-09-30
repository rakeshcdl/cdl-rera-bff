package com.cdl.escrow.workflow.mapper;

import com.cdl.escrow.entity.WorkflowDefinition;
import com.cdl.escrow.helper.EntityMapper;
import com.cdl.escrow.workflow.dto.WorkflowDefinitionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkflowDefinitionMapper extends EntityMapper<WorkflowDefinitionDTO, WorkflowDefinition> {

    @Mapping(source = "applicationModule", target = "applicationModuleDTO")
    @Mapping(source = "workflowAction", target = "workflowActionDTO")
    WorkflowDefinitionDTO toDto(WorkflowDefinition workflowDefinition);

    @Mapping(source = "applicationModuleDTO", target = "applicationModule")
    @Mapping(source = "workflowActionDTO", target = "workflowAction")
    WorkflowDefinition toEntity(WorkflowDefinitionDTO workflowDefinitionDTO);
}

