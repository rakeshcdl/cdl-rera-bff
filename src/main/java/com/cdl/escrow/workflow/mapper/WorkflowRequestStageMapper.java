package com.cdl.escrow.workflow.mapper;

import com.cdl.escrow.entity.WorkflowRequestStage;
import com.cdl.escrow.helper.EntityMapper;
import com.cdl.escrow.workflow.dto.WorkflowRequestStageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkflowRequestStageMapper extends EntityMapper<WorkflowRequestStageDTO, WorkflowRequestStage> {

    @Mapping(source = "workflowRequest", target = "workflowRequestDTO")
    @Mapping(source = "workflowRequestStageApprovals", target = "workflowRequestStageApprovalDTOS")
    @Mapping(source = "taskStatus", target = "taskStatusDTO")
    WorkflowRequestStageDTO toDto(WorkflowRequestStage workflowRequestStage);

    @Mapping(source = "workflowRequestDTO", target = "workflowRequest")
    @Mapping(source = "workflowRequestStageApprovalDTOS", target = "workflowRequestStageApprovals")
    @Mapping(source = "taskStatusDTO", target = "taskStatus")
    WorkflowRequestStage toEntity(WorkflowRequestStageDTO workflowRequestStageDTO);
}
