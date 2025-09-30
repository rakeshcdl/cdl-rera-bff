package com.cdl.escrow.workflow.service;

import com.cdl.escrow.entity.WorkflowRequest;

public interface WorkflowRuleEngineService {
    void applyAmountRules(WorkflowRequest request);
}
