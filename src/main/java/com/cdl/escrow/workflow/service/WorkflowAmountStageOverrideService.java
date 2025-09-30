package com.cdl.escrow.workflow.service;

import com.cdl.escrow.workflow.dto.WorkflowAmountStageOverrideDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WorkflowAmountStageOverrideService {

    Page<WorkflowAmountStageOverrideDTO> getAllWorkflowAmountStageOverride(final Pageable pageable);

    Optional<WorkflowAmountStageOverrideDTO> getWorkflowAmountStageOverrideById(Long id);

    WorkflowAmountStageOverrideDTO saveWorkflowAmountStageOverride(WorkflowAmountStageOverrideDTO workflowAmountStageOverrideDTO);

    WorkflowAmountStageOverrideDTO updateWorkflowAmountStageOverride(Long id, WorkflowAmountStageOverrideDTO workflowAmountStageOverrideDTO);

    Boolean deleteWorkflowAmountStageOverrideById(Long id);
}
