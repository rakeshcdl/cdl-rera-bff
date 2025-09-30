package com.cdl.escrow.workflow.service;

import com.cdl.escrow.workflow.dto.WorkflowRequestStageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WorkflowRequestStageService {

    Page<WorkflowRequestStageDTO> getAllWorkflowRequestStage(final Pageable pageable);

    Optional<WorkflowRequestStageDTO> getWorkflowRequestStageById(Long id);

    WorkflowRequestStageDTO saveWorkflowRequestStage(WorkflowRequestStageDTO workflowRequestStageDTO);

    WorkflowRequestStageDTO updateWorkflowRequestStage(Long id, WorkflowRequestStageDTO workflowRequestStageDTO);

    Boolean deleteWorkflowRequestStageById(Long id);
}
