package com.cdl.escrow.workflow.mapper;

import com.cdl.escrow.entity.WorkflowRequestLog;
import com.cdl.escrow.helper.EntityMapper;
import com.cdl.escrow.workflow.dto.WorkflowRequestLogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkflowRequestLogMapper extends EntityMapper<WorkflowRequestLogDTO, WorkflowRequestLog> {

    @Mapping(source = "workflowRequest", target = "workflowRequestDTO")
    WorkflowRequestLogDTO toDto(WorkflowRequestLog workflowRequestLog);

    @Mapping(source = "workflowRequestDTO", target = "workflowRequest")
    WorkflowRequestLog toEntity(WorkflowRequestLogDTO workflowRequestLogDTO);
}

