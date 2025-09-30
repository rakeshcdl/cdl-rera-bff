package com.cdl.escrow.workflow.serviceimpl;

import com.cdl.escrow.entity.WorkflowAmountRule;
import com.cdl.escrow.entity.WorkflowRequest;
import com.cdl.escrow.entity.WorkflowRequestStage;
import com.cdl.escrow.workflow.repository.WorkflowAmountRuleRepository;
import com.cdl.escrow.workflow.repository.WorkflowAmountStageOverrideRepository;
import com.cdl.escrow.workflow.service.WorkflowRuleEngineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WorkflowRuleEngineServiceImpl implements WorkflowRuleEngineService {

    private final WorkflowAmountRuleRepository amountRuleRepository;
    private final WorkflowAmountStageOverrideRepository overrideRepository;



    public void applyAmountRules(WorkflowRequest request) {
        Long definitionId = request.getWorkflowDefinition().getId();
        BigDecimal amount = request.getAmount();

        // 1. Find matching amount rule for this workflow definition + amount
        WorkflowAmountRule rule = amountRuleRepository
                .findApplicableRule(definitionId, amount)
                .orElse(null);

        if (rule == null) {
            return; // no amount-based rule found
        }

        // 2. Apply overrides for each stage
        for (WorkflowRequestStage stage : request.getWorkflowRequestStages()) {
            var overrideOpt = overrideRepository.findByAmountRuleIdAndStageOrder(rule.getId(), stage.getStageOrder());

            overrideOpt.ifPresent(override -> {
                if (override.getRequiredApprovals() != null) {
                    stage.setRequiredApprovals(override.getRequiredApprovals());
                }
                if (override.getKeycloakGroup() != null) {
                    stage.setKeycloakGroup(override.getKeycloakGroup());
                }
            });
        }
    }
}
