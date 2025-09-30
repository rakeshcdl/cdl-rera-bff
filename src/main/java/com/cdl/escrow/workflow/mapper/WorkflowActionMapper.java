package com.cdl.escrow.workflow.mapper;

import com.cdl.escrow.entity.WorkflowAction;
import com.cdl.escrow.helper.EntityMapper;
import com.cdl.escrow.workflow.dto.WorkflowActionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WorkflowActionMapper extends EntityMapper<WorkflowActionDTO, WorkflowAction> {

    WorkflowActionDTO toDto(WorkflowAction workflowAction);

    WorkflowAction toEntity(WorkflowActionDTO workflowActionDTO);
}
