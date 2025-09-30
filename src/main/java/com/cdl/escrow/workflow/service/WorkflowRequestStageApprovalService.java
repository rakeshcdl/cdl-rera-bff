package com.cdl.escrow.workflow.service;

import com.cdl.escrow.workflow.dto.WorkflowRequestStageApprovalDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WorkflowRequestStageApprovalService {

    Page<WorkflowRequestStageApprovalDTO> getAllWorkflowRequestStageApproval(final Pageable pageable);

    Optional<WorkflowRequestStageApprovalDTO> getWorkflowRequestStageApprovalById(Long id);

    WorkflowRequestStageApprovalDTO saveWorkflowRequestStageApproval(WorkflowRequestStageApprovalDTO workflowRequestStageApprovalDTO);

    WorkflowRequestStageApprovalDTO updateWorkflowRequestStageApproval(Long id, WorkflowRequestStageApprovalDTO workflowRequestStageApprovalDTO);

    Boolean deleteWorkflowRequestStageApprovalById(Long id);
}
