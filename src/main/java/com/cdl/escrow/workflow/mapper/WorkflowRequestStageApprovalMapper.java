package com.cdl.escrow.workflow.mapper;

import com.cdl.escrow.entity.WorkflowRequestStageApproval;
import com.cdl.escrow.helper.EntityMapper;
import com.cdl.escrow.mapper.ApplicationSettingMapper;
import com.cdl.escrow.workflow.dto.WorkflowRequestStageApprovalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring" , uses = {ApplicationSettingMapper.class, WorkflowRequestStageMapper.class})
public interface WorkflowRequestStageApprovalMapper extends EntityMapper<WorkflowRequestStageApprovalDTO, WorkflowRequestStageApproval> {

    @Mapping(source = "workflowRequestStage", target = "workflowRequestStageDTO")
    @Mapping(source = "taskStatus", target = "taskStatusDTO")
    WorkflowRequestStageApprovalDTO toDto(WorkflowRequestStageApproval workflowRequestStageApproval);

    @Mapping(source = "workflowRequestStageDTO", target = "workflowRequestStage")
    @Mapping(source = "taskStatusDTO", target = "taskStatus")
    WorkflowRequestStageApproval toEntity(WorkflowRequestStageApprovalDTO workflowRequestStageApprovalDTO);
}
