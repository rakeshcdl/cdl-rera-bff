package com.cdl.escrow.workflow.ext.service;

import com.cdl.escrow.entity.*;
import com.cdl.escrow.repository.TaskStatusRepository;
import com.cdl.escrow.workflow.ext.dto.*;
import com.cdl.escrow.workflow.ext.exception.DuplicateActionException;
import com.cdl.escrow.workflow.ext.exception.InvalidStateException;
import com.cdl.escrow.workflow.ext.exception.ResourceNotFoundException;
import com.cdl.escrow.workflow.ext.exception.UnauthorizedException;
import com.cdl.escrow.workflow.repository.WorkflowRequestLogRepository;
import com.cdl.escrow.workflow.repository.WorkflowRequestRepository;
import com.cdl.escrow.workflow.repository.WorkflowRequestStageApprovalRepository;
import com.cdl.escrow.workflow.repository.WorkflowRequestStageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkflowQueueService {
    private final WorkflowRequestRepository workflowRequestRepository;
    private final WorkflowRequestStageRepository workflowRequestStageRepository;
    private final WorkflowRequestStageApprovalRepository approvalRepository;
    private final TaskStatusRepository taskStatusRepository;
    private final WorkflowRequestLogRepository workflowLogRepository;

    /**
     * Get all pending workflow requests that are AWAITING ACTION by current user
     * These are requests where:
     * 1. Current stage matches user's group/role
     * 2. Stage is PENDING
     * 3. User has NOT already approved this stage
     */
   /* @Transactional(readOnly = true)
    public List<WorkflowQueueItemDTO> getAwaitingActions(String userId, List<String> userGroups, String moduleName) {
        log.info("Getting awaiting actions for user: {}, groups: {}, module: {}", userId, userGroups, moduleName);

        List<WorkflowRequestStage> pendingStages;

        if (moduleName != null && !moduleName.isEmpty()) {
            pendingStages = workflowRequestStageRepository
                    .findByKeycloakGroupInAndWorkflowRequest_ModuleNameAndCompletedAtIsNull(userGroups, moduleName);
        } else {
            pendingStages = workflowRequestStageRepository
                    .findByKeycloakGroupInAndCompletedAtIsNull(userGroups);
        }

        System.out.println("pendingStages::"+ pendingStages);
        List<WorkflowQueueItemDTO> awaitingActions = new ArrayList<>();

        for (WorkflowRequestStage stage : pendingStages) {
            // Check if user has already approved this stage
            boolean alreadyApproved = approvalRepository
                    .existsByWorkflowRequestStage_IdAndApproverUserId(stage.getId(), userId);
            System.out.println("alreadyApproved::"+ alreadyApproved);
            if (!alreadyApproved) {
                WorkflowQueueItemDTO dto = mapToQueueItemDTO(stage);
                dto.setPendingApprovals(stage.getRequiredApprovals() - stage.getApprovalsObtained());
                awaitingActions.add(dto);
            }
        }

        log.info("Found {} awaiting actions for user: {}", awaitingActions.size(), userId);
        return awaitingActions;
    }*/

    @Transactional(readOnly = true)
    public List<WorkflowQueueItemDTO> getAwaitingActions(String userId, List<String> userGroups, String moduleName) {
        log.info("Getting awaiting actions for user: {}, groups: {}, module: {}", userId, userGroups, moduleName);

        // This query finds stages that are:
        // 1. In user's group (keycloakGroup IN :groups)
        // 2. Have status = PENDING (only current active stage)
        // 3. Not deleted
        // 4. Parent workflow request not deleted
        List<WorkflowRequestStage> pendingStages;

        if (moduleName != null && !moduleName.isEmpty()) {
            pendingStages = workflowRequestStageRepository
                    .findByKeycloakGroupInAndWorkflowRequest_ModuleName(userGroups, moduleName);
        } else {
            pendingStages = workflowRequestStageRepository
                    .findByKeycloakGroupIn(userGroups);
        }

        List<WorkflowQueueItemDTO> awaitingActions = new ArrayList<>();

        for (WorkflowRequestStage stage : pendingStages) {
            // Additional validation: Ensure this is the CURRENT stage of the workflow
            WorkflowRequest request = stage.getWorkflowRequest();

            // Double-check: Stage must be the current active stage
            if (!stage.getStageOrder().equals(request.getCurrentStageOrder())) {
                log.warn("Stage {} is PENDING but not current stage for request {}. Skipping.",
                        stage.getId(), request.getId());
                continue;
            }

            // Check if user has already approved this stage
            boolean alreadyApproved = approvalRepository
                    .existsByWorkflowRequestStage_IdAndApproverUserId(stage.getId(), userId);

            if (!alreadyApproved) {
                WorkflowQueueItemDTO dto = mapToQueueItemDTO(stage);
                dto.setPendingApprovals(stage.getRequiredApprovals() - stage.getApprovalsObtained());
                awaitingActions.add(dto);
            }
        }

        log.info("Found {} awaiting actions for user: {}", awaitingActions.size(), userId);
        return awaitingActions;
    }

    /**
     * Get all workflow requests where user has ENGAGED (approved/rejected)
     * These are requests where:
     * 1. User has taken action (approved or rejected) on any stage
     * 2. Shows complete history of user's involvement
     */
    @Transactional(readOnly = true)
    public List<WorkflowEngagementDTO> getMyEngagements(String userId, String moduleName) {
        log.info("Getting engagements for user: {}, module: {}", userId, moduleName);

        List<WorkflowRequestStageApproval> userApprovals;

        if (moduleName != null && !moduleName.isEmpty()) {
            userApprovals = approvalRepository.findByApproverUserIdAndWorkflowRequestStage_WorkflowRequest_ModuleName(userId, moduleName);
        } else {
            userApprovals = approvalRepository.findByApproverUserId(userId);
        }

        List<WorkflowEngagementDTO> engagements = new ArrayList<>();

        for (WorkflowRequestStageApproval approval : userApprovals) {
            WorkflowRequestStage stage = approval.getWorkflowRequestStage();
            WorkflowRequest request = stage.getWorkflowRequest();

            WorkflowEngagementDTO dto = new WorkflowEngagementDTO();
            dto.setRequestId(request.getId());
            dto.setStageId(stage.getId());
            dto.setReferenceId(request.getReferenceId());
            dto.setReferenceType(request.getReferenceType());
            dto.setModuleName(request.getModuleName());
            dto.setActionKey(request.getActionKey());
            dto.setAmount(request.getAmount());
            dto.setCurrency(request.getCurrency());
            dto.setPayloadJson(request.getPayloadJson());

            dto.setStageOrder(stage.getStageOrder());
            dto.setStageKey(stage.getStageKey());
            dto.setStageName(getStageName(stage.getStageKey()));
            dto.setStageStatus(stage.getTaskStatus().getCode());

            dto.setMyDecision(approval.getTaskStatus().getCode());
            dto.setMyRemarks(approval.getRemarks());
            dto.setMyActionDate(approval.getDecidedAt());

            dto.setRequestStatus(request.getTaskStatus().getCode());
            dto.setCurrentStageOrder(request.getCurrentStageOrder());
            dto.setCreatedBy(request.getCreatedBy());
            dto.setCreatedAt(request.getCreatedAt());

            engagements.add(dto);
        }

        // Sort by action date descending
        engagements.sort((a, b) -> b.getMyActionDate().compareTo(a.getMyActionDate()));

        log.info("Found {} engagements for user: {}", engagements.size(), userId);
        return engagements;
    }

    /**
     * Get detailed view of a specific workflow request
     * Only accessible if:
     * - User is in current stage group, OR
     * - User has previously engaged with this request
     */
    @Transactional(readOnly = true)
    public WorkflowRequestDetailDTO getWorkflowRequestDetail(Long requestId, String userId, List<String> userGroups) {
        log.info("Getting workflow request detail for requestId: {}, user: {}", requestId, userId);

        WorkflowRequest request = workflowRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Workflow request not found: " + requestId));

        // Check access permission
        boolean hasAccess = checkUserAccessToRequest(request, userId, userGroups);

        if (!hasAccess) {
            throw new UnauthorizedException("User does not have access to this workflow request");
        }

        WorkflowRequestDetailDTO dto = new WorkflowRequestDetailDTO();
        dto.setId(request.getId());
        dto.setReferenceId(request.getReferenceId());
        dto.setReferenceType(request.getReferenceType());
        dto.setModuleName(request.getModuleName());
        dto.setActionKey(request.getActionKey());
        dto.setAmount(request.getAmount());
        dto.setCurrency(request.getCurrency());
        dto.setPayloadJson(request.getPayloadJson());
        dto.setStatus(request.getTaskStatus().getCode());
        dto.setCurrentStageOrder(request.getCurrentStageOrder());
        dto.setCreatedBy(request.getCreatedBy());
        dto.setCreatedAt(request.getCreatedAt());
        dto.setLastUpdatedAt(request.getLastUpdatedAt());

        // Get all stages
        List<WorkflowRequestStage> stages = workflowRequestStageRepository
                .findByWorkflowRequest_Id(requestId);

        List<WorkflowStageDetailDTO> stageDTOs = new ArrayList<>();
        for (WorkflowRequestStage stage : stages) {
            WorkflowStageDetailDTO stageDTO = mapToStageDetailDTO(stage);

            // Get approvals for this stage
            List<WorkflowRequestStageApproval> approvals = approvalRepository
                    .findByWorkflowRequestStage_Id(stage.getId());

            List<WorkflowApprovalDTO> approvalDTOs = approvals.stream()
                    .map(this::mapToApprovalDTO)
                    .collect(Collectors.toList());

            stageDTO.setApprovals(approvalDTOs);
            stageDTO.setCanUserApprove(canUserApproveStage(stage, userId, userGroups));

            stageDTOs.add(stageDTO);
        }

        dto.setStages(stageDTOs);

        // Get logs
        List<WorkflowRequestLog> logs = workflowLogRepository.findByWorkflowRequest_Id(requestId);
        List<WorkflowLogDTO> logDTOs = logs.stream()
                .map(this::mapToLogDTO)
                .collect(Collectors.toList());
        dto.setLogs(logDTOs);

        return dto;
    }

    /**
     * Check if user has access to view the workflow request
     */
    private boolean checkUserAccessToRequest(WorkflowRequest request, String userId, List<String> userGroups) {
        // Get current stage
        Optional<WorkflowRequestStage> currentStageOpt = workflowRequestStageRepository
                .findByWorkflowRequest_IdAndStageOrder(request.getId(), request.getCurrentStageOrder());

        if (currentStageOpt.isPresent()) {
            WorkflowRequestStage currentStage = currentStageOpt.get();

            // User can access if they are in current stage group
            if (userGroups.contains(currentStage.getKeycloakGroup())) {
                return true;
            }
        }

        // User can access if they have previously engaged with any stage
        List<WorkflowRequestStage> allStages = workflowRequestStageRepository
                .findByWorkflowRequest_Id(request.getId());

        for (WorkflowRequestStage stage : allStages) {
            boolean hasEngaged = approvalRepository
                    .existsByWorkflowRequestStage_IdAndApproverUserId(stage.getId(), userId);
            if (hasEngaged) {
                return true;
            }
        }

        // User can access if they created the request
        if (userId.equals(request.getCreatedBy())) {
            return true;
        }

        return false;
    }

    /**
     * Check if user can approve a specific stage
     */
    private boolean canUserApproveStage(WorkflowRequestStage stage, String userId, List<String> userGroups) {
        // Stage must be pending
        if (!"PENDING".equals(stage.getTaskStatus().getCode())) {
            return false;
        }

        // User must be in stage group
        if (!userGroups.contains(stage.getKeycloakGroup())) {
            return false;
        }

        // User must not have already approved
        boolean alreadyApproved = approvalRepository
                .existsByWorkflowRequestStage_IdAndApproverUserId(stage.getId(), userId);

        return !alreadyApproved;
    }

    /**
     * Log workflow event
     */
    private void logWorkflowEvent(WorkflowRequest request, String eventType,
                                  String userId, String userGroup, Map<String, Object> details) {
        WorkflowRequestLog log = new WorkflowRequestLog();
        log.setWorkflowRequest(request);
        log.setEventType(eventType);
        log.setEventByUser(userId);
        log.setEventByGroup(userGroup);
        log.setDetailsJson(details);
        log.setEnabled(true);
        log.setDeleted(false);

        workflowLogRepository.save(log);
    }

    // Helper mapping methods
    private WorkflowQueueItemDTO mapToQueueItemDTO(WorkflowRequestStage stage) {
        WorkflowRequest request = stage.getWorkflowRequest();

        WorkflowQueueItemDTO dto = new WorkflowQueueItemDTO();
        dto.setRequestId(request.getId());
        dto.setStageId(stage.getId());
        dto.setReferenceId(request.getReferenceId());
        dto.setReferenceType(request.getReferenceType());
        dto.setModuleName(request.getModuleName());
        dto.setActionKey(request.getActionKey());
        dto.setAmount(request.getAmount());
        dto.setCurrency(request.getCurrency());
        dto.setPayloadJson(request.getPayloadJson());
        dto.setStageOrder(stage.getStageOrder());
        dto.setStageKey(stage.getStageKey());
        dto.setRequiredApprovals(stage.getRequiredApprovals());
        dto.setApprovalsObtained(stage.getApprovalsObtained());
        dto.setStartedAt(stage.getStartedAt());
        dto.setCreatedAt(request.getCreatedAt());
        dto.setCreatedBy(request.getCreatedBy());

        return dto;
    }

    private WorkflowStageDetailDTO mapToStageDetailDTO(WorkflowRequestStage stage) {
        WorkflowStageDetailDTO dto = new WorkflowStageDetailDTO();
        dto.setId(stage.getId());
        dto.setStageOrder(stage.getStageOrder());
        dto.setStageKey(stage.getStageKey());
        dto.setStageName(getStageName(stage.getStageKey()));
        dto.setKeycloakGroup(stage.getKeycloakGroup());
        dto.setRequiredApprovals(stage.getRequiredApprovals());
        dto.setApprovalsObtained(stage.getApprovalsObtained());
        dto.setStatus(stage.getTaskStatus().getCode());
        dto.setStartedAt(stage.getStartedAt());
        dto.setCompletedAt(stage.getCompletedAt());

        return dto;
    }

    private WorkflowApprovalDTO mapToApprovalDTO(WorkflowRequestStageApproval approval) {
        WorkflowApprovalDTO dto = new WorkflowApprovalDTO();
        dto.setId(approval.getId());
        dto.setApproverUserId(approval.getApproverUserId());
        dto.setApproverUsername(approval.getApproverUsername());
        dto.setApproverGroup(approval.getApproverGroup());
        dto.setDecision(approval.getTaskStatus().getCode());
        dto.setRemarks(approval.getRemarks());
        dto.setDecidedAt(approval.getDecidedAt());

        return dto;
    }

    private WorkflowLogDTO mapToLogDTO(WorkflowRequestLog log) {
        WorkflowLogDTO dto = new WorkflowLogDTO();
        dto.setId(log.getId());
        dto.setEventType(log.getEventType());
        dto.setEventByUser(log.getEventByUser());
        dto.setEventByGroup(log.getEventByGroup());
        dto.setEventAt(log.getEventAt());
        dto.setDetailsJson(log.getDetailsJson());

        return dto;
    }

    private String getStageName(String stageKey) {
        // Map stage keys to readable names
        Map<String, String> stageNames = Map.of(
                "MAKER", "Maker Review",
                "CHECKER", "Checker Review",
                "REVIEWER", "Reviewer Assessment",
                "ADMIN", "Admin Approval"
        );
        return stageNames.getOrDefault(stageKey, stageKey);
    }

    private String getNextStageName(WorkflowRequest request) {
        Optional<WorkflowRequestStage> nextStageOpt = workflowRequestStageRepository
                .findByWorkflowRequest_IdAndStageOrder(request.getId(), request.getCurrentStageOrder());

        return nextStageOpt.map(stage -> getStageName(stage.getStageKey())).orElse("Completed");
    }

    /**
     * Get workflow summary statistics
     */
    @Transactional(readOnly = true)
    public WorkflowSummaryDTO getWorkflowSummary(String userId, List<String> userGroups) {
        log.info("Getting workflow summary for user: {}", userId);

        WorkflowSummaryDTO summary = new WorkflowSummaryDTO();

        // Get awaiting actions
        List<WorkflowRequestStage> pendingStages = workflowRequestStageRepository
                .findByKeycloakGroupInAndCompletedAtIsNull(userGroups);

        // Filter out stages where user has already approved
        List<WorkflowQueueItemDTO> awaitingActions = new ArrayList<>();
        for (WorkflowRequestStage stage : pendingStages) {
            boolean alreadyApproved = approvalRepository
                    .existsByWorkflowRequestStage_IdAndApproverUserId(stage.getId(), userId);
            if (!alreadyApproved) {
                awaitingActions.add(mapToQueueItemDTO(stage));
            }
        }

        // Get engagements
        List<WorkflowRequestStageApproval> userApprovals = approvalRepository.findByApproverUserId(userId);

        // Calculate totals
        summary.setTotalAwaitingActions(awaitingActions.size());
        summary.setTotalEngagements(userApprovals.size());

        // Group awaiting actions by module
        Map<String, Integer> awaitingByModule = awaitingActions.stream()
                .collect(Collectors.groupingBy(
                        WorkflowQueueItemDTO::getModuleName,
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
        summary.setAwaitingActionsByModule(awaitingByModule);

        // Group engagements by module
        Map<String, Integer> engagementsByModule = userApprovals.stream()
                .collect(Collectors.groupingBy(
                        approval -> approval.getWorkflowRequestStage().getWorkflowRequest().getModuleName(),
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
        summary.setEngagementsByModule(engagementsByModule);

        // Group awaiting actions by stage
        Map<String, Integer> awaitingByStage = awaitingActions.stream()
                .collect(Collectors.groupingBy(
                        item -> getStageName(item.getStageKey()),
                        Collectors.collectingAndThen(Collectors.counting(), Long::intValue)
                ));
        summary.setAwaitingActionsByStage(awaitingByStage);

        log.info("Summary - Awaiting: {}, Engagements: {}",
                summary.getTotalAwaitingActions(), summary.getTotalEngagements());

        return summary;
    }


    /**
     * Process approval/rejection decision
     */
    @Transactional
    public WorkflowDecisionResponseDTO processDecision(Long stageId, WorkflowDecisionDTO decisionDTO) {
        log.info("Processing decision for stage: {}, user: {}, decision: {}",
                stageId, decisionDTO.getUserId(), decisionDTO.getDecision());

        WorkflowRequestStage stage = workflowRequestStageRepository.findById(stageId)
                .orElseThrow(() -> new ResourceNotFoundException("Stage not found: " + stageId));

        WorkflowRequest request = stage.getWorkflowRequest();

        // Validate stage is pending
        if (!"PENDING".equals(stage.getTaskStatus().getCode())) {
            throw new InvalidStateException("Stage is not in pending state");
        }

        // Validate user hasn't already approved
        boolean alreadyApproved = approvalRepository
                .existsByWorkflowRequestStage_IdAndApproverUserId(stageId, decisionDTO.getUserId());

        if (alreadyApproved) {
            throw new DuplicateActionException("User has already taken action on this stage");
        }

        // Validate user group
        if (!decisionDTO.getUserGroup().equals(stage.getKeycloakGroup())) {
            throw new UnauthorizedException("User not authorized for this stage");
        }

        TaskStatus approvalStatus = taskStatusRepository.findByCode(decisionDTO.getDecision())
                .orElseThrow(() -> new ResourceNotFoundException("Task status not found: " + decisionDTO.getDecision()));

        // Create approval record
        WorkflowRequestStageApproval approval = new WorkflowRequestStageApproval();
        approval.setWorkflowRequestStage(stage);
        approval.setApproverUserId(decisionDTO.getUserId());
        approval.setApproverUsername(decisionDTO.getUsername());
        approval.setApproverGroup(decisionDTO.getUserGroup());
        approval.setRemarks(decisionDTO.getRemarks());
        approval.setTaskStatus(approvalStatus);
        approval.setEnabled(true);
        approval.setDeleted(false);

        approvalRepository.save(approval);

        // Log the approval
        logWorkflowEvent(request, "STAGE_" + decisionDTO.getDecision(),
                decisionDTO.getUserId(), decisionDTO.getUserGroup(),
                Map.of(
                        "stageOrder", stage.getStageOrder(),
                        "stageKey", stage.getStageKey(),
                        "remarks", decisionDTO.getRemarks() != null ? decisionDTO.getRemarks() : ""
                ));

        WorkflowDecisionResponseDTO response = new WorkflowDecisionResponseDTO();

        if ("REJECTED".equals(decisionDTO.getDecision())) {
            // Handle rejection - stop workflow
            handleRejection(stage, request);
            response.setMessage("Workflow request rejected");
            response.setWorkflowStatus("REJECTED");
            response.setNextStage(null);
        } else if ("APPROVED".equals(decisionDTO.getDecision())) {
            // Handle approval
            stage.setApprovalsObtained(stage.getApprovalsObtained() + 1);
            workflowRequestStageRepository.save(stage);

            // Check if stage is complete
            if (stage.getApprovalsObtained() >= stage.getRequiredApprovals()) {
                completeStage(stage, request);
                response.setMessage("Stage completed successfully");
                response.setNextStage(getNextStageName(request));
            } else {
                response.setMessage("Approval recorded. Waiting for more approvals");
                response.setNextStage(stage.getStageKey());
            }

            response.setWorkflowStatus(request.getTaskStatus().getCode());
        }

        response.setRequestId(request.getId());
        response.setStageId(stageId);
        response.setCurrentStageOrder(request.getCurrentStageOrder());

        return response;
    }

    /**
     * Handle stage rejection
     */
    private void handleRejection(WorkflowRequestStage stage, WorkflowRequest request) {
        // Mark stage as rejected
        TaskStatus rejectedStatus = taskStatusRepository.findByCode("REJECTED")
                .orElseThrow(() -> new ResourceNotFoundException("Rejected status not found"));

        stage.setTaskStatus(rejectedStatus);
        stage.setCompletedAt(ZonedDateTime.now());
        workflowRequestStageRepository.save(stage);

        // Mark request as rejected
        request.setTaskStatus(rejectedStatus);
        workflowRequestRepository.save(request);

        // Log workflow rejection
        logWorkflowEvent(request, "WORKFLOW_REJECTED", null, null,
                Map.of("stageOrder", stage.getStageOrder(), "stageKey", stage.getStageKey()));
    }

    /**
     * Complete current stage and move to next
     */
    private void completeStage(WorkflowRequestStage stage, WorkflowRequest request) {
        // Mark current stage as completed
        TaskStatus completedStatus = taskStatusRepository.findByCode("COMPLETED")
                .orElseThrow(() -> new ResourceNotFoundException("Completed status not found"));

        stage.setTaskStatus(completedStatus);
        stage.setCompletedAt(ZonedDateTime.now());
        workflowRequestStageRepository.save(stage);

        // Log stage completion
        logWorkflowEvent(request, "STAGE_COMPLETED", null, null,
                Map.of("stageOrder", stage.getStageOrder(), "stageKey", stage.getStageKey()));

        // Get next stage
        Optional<WorkflowRequestStage> nextStageOpt = workflowRequestStageRepository
                .findByWorkflowRequest_IdAndStageOrder(request.getId(), stage.getStageOrder() + 1);

        if (nextStageOpt.isPresent()) {
            // Start next stage
            WorkflowRequestStage nextStage = nextStageOpt.get();
            TaskStatus pendingStatus = taskStatusRepository.findByCode("PENDING")
                    .orElseThrow(() -> new ResourceNotFoundException("Pending status not found"));

            nextStage.setTaskStatus(pendingStatus);
            nextStage.setStartedAt(ZonedDateTime.now());
            workflowRequestStageRepository.save(nextStage);

            // Update request current stage
            request.setCurrentStageOrder(nextStage.getStageOrder());
            workflowRequestRepository.save(request);

            // Log next stage start
            logWorkflowEvent(request, "STAGE_STARTED", null, null,
                    Map.of("stageOrder", nextStage.getStageOrder(),
                            "stageKey", nextStage.getStageKey(),
                            "group", nextStage.getKeycloakGroup()));
        } else {
            // No more stages - workflow complete
            TaskStatus approvedStatus = taskStatusRepository.findByCode("APPROVED")
                    .orElseThrow(() -> new ResourceNotFoundException("Approved status not found"));

            request.setTaskStatus(approvedStatus);
            workflowRequestRepository.save(request);

            // Log workflow completion
            logWorkflowEvent(request, "WORKFLOW_COMPLETED", null, null, Map.of());
        }
    }
}
