package com.cdl.escrow.workflow.service;

import com.cdl.escrow.enumeration.WorkflowDecision;

public interface WorkflowExecutionService {

    void executeStage(Long stageId, String userId, WorkflowDecision decision, String remarks) throws Exception;
}
