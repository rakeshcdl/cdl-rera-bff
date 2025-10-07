package com.cdl.escrow.workflow.serviceimpl;

import com.cdl.escrow.entity.TaskStatus;
import com.cdl.escrow.entity.WorkflowRequest;
import com.cdl.escrow.entity.WorkflowRequestStage;
import com.cdl.escrow.entity.WorkflowRequestStageApproval;
import com.cdl.escrow.enumeration.WorkflowDecision;
import com.cdl.escrow.enumeration.WorkflowEvent;
import com.cdl.escrow.exception.WorkflowException;
import com.cdl.escrow.repository.TaskStatusRepository;
import com.cdl.escrow.service.AuthAdminRoleService;
import com.cdl.escrow.service.AuthAdminUserService;
import com.cdl.escrow.workflow.repository.WorkflowRequestRepository;
import com.cdl.escrow.workflow.repository.WorkflowRequestStageApprovalRepository;
import com.cdl.escrow.workflow.repository.WorkflowRequestStageRepository;
import com.cdl.escrow.workflow.service.WorkflowExecutionService;
import com.cdl.escrow.workflow.service.WorkflowRequestLogService;
import com.cdl.escrow.workflow.service.WorkflowRuleEngineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkflowExecutionServiceImpl implements WorkflowExecutionService {

    private final WorkflowRequestStageRepository stageRepository;

    private final WorkflowRequestStageApprovalRepository approvalRepository;

    private final WorkflowRequestRepository requestRepository;

    private final WorkflowRequestLogService logService;

    private final WorkflowRuleEngineService workflowRuleEngineService;

    private final WorkflowFinalizationService finalizationService;

    private final TaskStatusRepository taskStatusRepository;

    private final AuthAdminUserService authAdminUserService;

    @Override
    @Transactional
    public void executeStage(Long stageId, String userId,
                             WorkflowDecision decision, String remarks) throws Exception {
        WorkflowRequestStage stage = stageRepository.findById(stageId)
                .orElseThrow(() -> new WorkflowException("Stage not found: " + stageId));

        WorkflowRequest request = stage.getWorkflowRequest();

        // Fetch user roles for the userId (implement as per your user management)
        List<String> userRoles = getUserRolesByUserId(userId);

        System.out.println("userRoles::"+userRoles);
        // Enforce restriction: If Maker approval pending or in progress,
        // Checker or Admin cannot approve or reject
        if (isMakerApprovalPendingOrInProgress(request) &&
                (userRoles.contains("ROLE_CHECKER") || userRoles.contains("ROLE_ADMIN"))) {
            throw new WorkflowException("Checker and Admin users cannot approve or reject while Maker approval is pending or in progress.");
        }

        // Apply dynamic workflow rules before execution
        workflowRuleEngineService.applyAmountRules(request);

        // Save decision
        WorkflowRequestStageApproval approval = buildApproval(stage, userId, decision, remarks);
        approvalRepository.save(approval);

        if (decision == WorkflowDecision.REJECT) {
            handleRejection(stage, request, userId, decision, remarks);
            return;
        }

        handleApproval(stage, request, userId, decision, remarks);
    }

    private WorkflowRequestStageApproval buildApproval(WorkflowRequestStage stage, String userId,
                                                       WorkflowDecision decision, String remarks) {
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();

        WorkflowRequestStageApproval approval = new WorkflowRequestStageApproval();
        TaskStatus requestStatus = findTaskStatus(taskStatusList, "APPROVED", "WORKFLOW_COMPLETED","STAGE_COMPLETED");
        approval.setWorkflowRequestStage(stage);
        approval.setApproverUserId(userId);
        // approval.setWorkflowDecision(decision); // Uncomment if decision stored
        approval.setTaskStatus(requestStatus);
        approval.setRemarks(remarks);
        approval.setDecidedAt(ZonedDateTime.now());
        return approval;
    }

    private void handleRejection(WorkflowRequestStage stage, WorkflowRequest request,
                                 String userId, WorkflowDecision decision, String remarks) {

        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();

        TaskStatus requestStatus = findTaskStatus(taskStatusList, "REJECTED", "WORKFLOW_REJECTED","STAGE_REJECTED");
        stage.setCompletedAt(ZonedDateTime.now());
        request.setTaskStatus(requestStatus);

        stageRepository.save(stage);
        requestRepository.save(request);

        logService.logDecision(request, stage, userId, decision, remarks);
        logService.logStageEvent(request, stage, String.valueOf(WorkflowEvent.STAGE_REJECTED),
                Map.of("stageKey", stage.getStageKey(), "stageOrder", stage.getStageOrder()));
        logService.logWorkflowEvent(request, String.valueOf(WorkflowEvent.WORKFLOW_REJECTED),
                Map.of("message", "Workflow rejected at stage " + stage.getStageKey()));

        // Finalize module on rejection
        finalizationService.finalizeWorkflow(request);
    }

    private void handleApproval(WorkflowRequestStage stage, WorkflowRequest request,
                                String userId, WorkflowDecision decision, String remarks) {
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();

        TaskStatus requestStatus = findTaskStatus(taskStatusList, "APPROVED", "WORKFLOW_COMPLETED","STAGE_COMPLETED");

        stage.setApprovalsObtained(stage.getApprovalsObtained() + 1);
        stageRepository.save(stage);

        logService.logDecision(request, stage, userId, decision, remarks);

        if (stage.getApprovalsObtained() >= stage.getRequiredApprovals()) {
            stage.setTaskStatus(requestStatus);
            stage.setCompletedAt(ZonedDateTime.now());
            stageRepository.save(stage);

            logService.logStageEvent(request, stage, String.valueOf(WorkflowEvent.STAGE_COMPLETED),
                    Map.of("stageKey", stage.getStageKey(), "stageOrder", stage.getStageOrder()));

            autoProgressToNextStage(request, stage);
        }
    }

    private void autoProgressToNextStage(WorkflowRequest request, WorkflowRequestStage currentStage) {
        int nextOrder = currentStage.getStageOrder() + 1;

        WorkflowRequestStage nextStage = request.getWorkflowRequestStages().stream()
                .filter(s -> s.getStageOrder() == nextOrder)
                .findFirst()
                .orElse(null);

        if (nextStage != null) {
            nextStage.setStartedAt(ZonedDateTime.now());
            stageRepository.save(nextStage);
            requestRepository.save(request);
            logService.logStageEvent(request, nextStage, String.valueOf(WorkflowEvent.STAGE_STARTED),
                    Map.of("stageKey", nextStage.getStageKey(), "stageOrder", nextStage.getStageOrder()));
        } else {
            requestRepository.save(request);

            logService.logWorkflowEvent(request, String.valueOf(WorkflowEvent.WORKFLOW_COMPLETED),
                    Map.of("message", "Workflow completed successfully"));

            // Finalize module on approval
            finalizationService.finalizeWorkflow(request);
        }
    }

    /**
     * Helper: Check if Maker approval is pending or in progress
     */
    private boolean isMakerApprovalPendingOrInProgress(WorkflowRequest request) {
        return request.getWorkflowRequestStages().stream().anyMatch(stage ->
                "MAKER".equalsIgnoreCase(stage.getStageKey()) &&
                        stage.getCompletedAt() == null && // Not completed
                        stage.getTaskStatus() != null &&
                        (
                                "IN_PROGRESS".equalsIgnoreCase(stage.getTaskStatus().getCode()) ||
                                        "PENDING".equalsIgnoreCase(stage.getTaskStatus().getCode())
                        )
        );
    }

    /**
     * Helper: Find user roles by user ID (stub, implement with real logic)
     */
    private List<String> getUserRolesByUserId(String userId) throws Exception {
        try {
            List<RoleRepresentation> roles = authAdminUserService.findOneRole(userId);
            if (roles == null || roles.isEmpty()) {
                return Collections.emptyList();  // Return immutable empty list instead of mutable list
            }

            // Use a stream to map RoleRepresentation to role name and collect as a list
            return roles.stream()
                    .map(RoleRepresentation::getName)
                    .filter(Objects::nonNull)
                    .distinct()  // Avoid duplicate roles
                    .collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error fetching roles for userId {}: {}", userId, e.getMessage());
            throw e;  // Propagate exception after logging
        }
    }


    /**
     * Helper: find TaskStatus by preferred codes (case-insensitive).
     * Returns first matching TaskStatus or null.
     */
    private TaskStatus findTaskStatus(List<TaskStatus> list, String... preferredCodes) {
        if (list == null || list.isEmpty() || preferredCodes == null) return null;

        Set<String> prefs = Arrays.stream(preferredCodes)
                .filter(Objects::nonNull)
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        // try to match by code first
        for (TaskStatus ts : list) {
            if (ts == null) continue;
            String code = ts.getCode() == null ? "" : ts.getCode().trim().toLowerCase();
            if (prefs.contains(code)) return ts;
        }
        // try to match by name if code didn't match
        for (TaskStatus ts : list) {
            if (ts == null) continue;
            String name = ts.getName() == null ? "" : ts.getName().trim().toLowerCase();
            if (prefs.contains(name)) return ts;
        }
        return null;
    }

    /**
     * Helper: find by name case-insensitive
     */
    private TaskStatus findTaskStatusByName(List<TaskStatus> list, String name) {
        if (list == null || list.isEmpty() || name == null) return null;
        String target = name.trim().toLowerCase();
        return list.stream()
                .filter(Objects::nonNull)
                .filter(ts -> ts.getName() != null && ts.getName().trim().toLowerCase().equals(target))
                .findFirst()
                .orElse(null);
    }
}

