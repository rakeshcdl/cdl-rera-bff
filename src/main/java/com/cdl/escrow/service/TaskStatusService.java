package com.cdl.escrow.service;


import com.cdl.escrow.dto.TaskStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TaskStatusService {

    Page<TaskStatusDTO> getAllTaskStatus(final Pageable pageable);

    Optional<TaskStatusDTO> getTaskStatusyId(Long id);

    TaskStatusDTO saveTaskStatus(TaskStatusDTO taskStatusDTO);

    TaskStatusDTO updateTaskStatus(Long id, TaskStatusDTO taskStatusDTO);

    Boolean deleteTaskStatusById(Long id);

    boolean softTaskStatusServiceById(Long id);
}
