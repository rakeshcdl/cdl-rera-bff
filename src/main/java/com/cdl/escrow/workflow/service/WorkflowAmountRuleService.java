package com.cdl.escrow.workflow.service;

import com.cdl.escrow.workflow.dto.WorkflowAmountRuleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

public interface WorkflowAmountRuleService {

    Page<WorkflowAmountRuleDTO> getAllWorkflowAmountRule(final Pageable pageable);

    Optional<WorkflowAmountRuleDTO> getWorkflowAmountRuleById(Long id);

    WorkflowAmountRuleDTO saveWorkflowAmountRule(WorkflowAmountRuleDTO workflowAmountRuleDTO);

    WorkflowAmountRuleDTO updateWorkflowAmountRule(Long id, WorkflowAmountRuleDTO workflowAmountRuleDTO);

    Boolean deleteWorkflowAmountRuleById(Long id);

    WorkflowAmountRuleDTO resolveRule(Long workflowId, BigDecimal amount);
}
