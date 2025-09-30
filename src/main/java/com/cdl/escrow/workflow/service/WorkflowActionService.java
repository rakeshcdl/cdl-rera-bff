package com.cdl.escrow.workflow.service;

import com.cdl.escrow.workflow.dto.WorkflowActionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WorkflowActionService {

    Page<WorkflowActionDTO> getAllWorkflowAction(final Pageable pageable);

    Optional<WorkflowActionDTO> getWorkflowActionById(Long id);

    WorkflowActionDTO saveWorkflowAction(WorkflowActionDTO workflowActionDTO);

    WorkflowActionDTO updateWorkflowAction(Long id, WorkflowActionDTO workflowActionDTO);

    Boolean deleteWorkflowActionById(Long id);

    Page<WorkflowActionDTO> search(String moduleCode, String keyword, Pageable pageable);
}
