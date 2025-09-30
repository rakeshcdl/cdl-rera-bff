package com.cdl.escrow.workflow.service;

import com.cdl.escrow.entity.WorkflowRequest;
import com.cdl.escrow.entity.WorkflowRequestStage;
import com.cdl.escrow.enumeration.WorkflowDecision;
import com.cdl.escrow.workflow.dto.WorkflowRequestLogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WorkflowRequestLogService {

    Page<WorkflowRequestLogDTO> getAllWorkflowRequestLog(final Pageable pageable);

    Optional<WorkflowRequestLogDTO> getWorkflowRequestLogById(Long id);

    WorkflowRequestLogDTO saveWorkflowRequestLog(WorkflowRequestLogDTO workflowRequestLogDTO);

    WorkflowRequestLogDTO updateWorkflowRequestLog(Long id, WorkflowRequestLogDTO workflowRequestLogDTO);

    Boolean deleteWorkflowRequestLogById(Long id);

    // Logs when a user takes an explicit decision (Approve/Reject/Return)
    void logDecision(WorkflowRequest request,
                     WorkflowRequestStage stage,
                     String userId,
                     WorkflowDecision decision,
                     String remarks);

    // Logs system or stage lifecycle events
    void logStageEvent(WorkflowRequest request,
                       WorkflowRequestStage stage,
                       String eventType,  // e.g. STAGE_STARTED, STAGE_COMPLETED
                       Object details);

    // Logs workflow-level lifecycle events
    void logWorkflowEvent(WorkflowRequest request,
                          String eventType, // e.g. WORKFLOW_COMPLETED
                          Object details);
}
