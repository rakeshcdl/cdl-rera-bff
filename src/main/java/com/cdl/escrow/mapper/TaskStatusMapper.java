package com.cdl.escrow.mapper;

import com.cdl.escrow.dto.TaskStatusDTO;
import com.cdl.escrow.entity.TaskStatus;
import com.cdl.escrow.helper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskStatusMapper  extends EntityMapper<TaskStatusDTO, TaskStatus> {
    TaskStatusDTO toDto(TaskStatus taskStatus);
    TaskStatus toEntity(TaskStatusDTO taskStatusDTO);
}

