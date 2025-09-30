package com.cdl.escrow.workflow.mapper;

import com.cdl.escrow.entity.WorkflowRequest;
import com.cdl.escrow.helper.EntityMapper;
import com.cdl.escrow.workflow.dto.WorkflowRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkflowRequestMapper extends EntityMapper<WorkflowRequestDTO, WorkflowRequest> {

    @Mapping(source = "workflowDefinition", target = "workflowDefinitionDTO")
    @Mapping(source = "workflowRequestStages", target = "workflowRequestStageDTOS")
    @Mapping(source = "taskStatus", target = "taskStatusDTO")
    WorkflowRequestDTO toDto(WorkflowRequest workflowRequest);

    @Mapping(source = "workflowDefinitionDTO", target = "workflowDefinition")
    @Mapping(source = "workflowRequestStageDTOS", target = "workflowRequestStages")
    @Mapping(source = "taskStatusDTO", target = "taskStatus")
    WorkflowRequest toEntity(WorkflowRequestDTO workflowRequestDTO);
}
