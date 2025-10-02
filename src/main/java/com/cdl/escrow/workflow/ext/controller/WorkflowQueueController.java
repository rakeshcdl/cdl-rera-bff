package com.cdl.escrow.workflow.ext.controller;

import com.cdl.escrow.workflow.ext.dto.*;
import com.cdl.escrow.workflow.ext.service.WorkflowQueueService;
import com.cdl.escrow.workflow.ext.service.WorkflowRequestCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/workflow")
@RequiredArgsConstructor
@Slf4j
public class WorkflowQueueController {

    private final WorkflowQueueService workflowQueueService;
    private final WorkflowRequestCheckService workflowRequestService;

    /**
     * Get all workflow requests awaiting action by current user
     *
     * @param moduleName Optional filter by module (PROJECT, DEVELOPER, PAYMENT, DEPOSIT)
     * @param authentication Spring Security authentication object
     * @return List of workflow requests where user can take action
     */

    @GetMapping("/awaiting-actions")
    public ResponseEntity<ApiResponse<List<WorkflowQueueItemDTO>>> getAwaitingActions(
            @RequestParam(required = false) String moduleName,
            Authentication authentication) {

        log.info("Fetching awaiting actions for user with module filter: {}", moduleName);

        // Extract user ID and groups from authentication
        String userId = authentication.getName();
        List<String> userGroups = extractUserGroups(authentication);
        System.out.println("Awaiting userId" + userId + " and userGroups" + userGroups);

        List<WorkflowQueueItemDTO> awaitingActions = workflowQueueService
                .getAwaitingActions(userId, userGroups, moduleName);

        return ResponseEntity.ok(ApiResponse.success(awaitingActions,
                "Retrieved " + awaitingActions.size() + " requests awaiting action"));
    }


    /**
     * Get all workflow requests where current user has engaged
     * Shows history of user's approvals/rejections
     *
     * @param moduleName Optional filter by module
     * @param authentication Spring Security authentication object
     * @return List of workflow requests where user has taken action
     */
    @GetMapping("/my-engagements")
    public ResponseEntity<ApiResponse<List<WorkflowEngagementDTO>>> getMyEngagements(
            @RequestParam(required = false) String moduleName,
            Authentication authentication) {

        log.info("Fetching engagements for user with module filter: {}", moduleName);

        String userId = authentication.getName();
        System.out.println(" My engagements userId" + userId + " and userGroups" );
        List<WorkflowEngagementDTO> engagements = workflowQueueService
                .getMyEngagements(userId, moduleName);

        return ResponseEntity.ok(ApiResponse.success(engagements,
                "Retrieved " + engagements.size() + " engagements"));
    }

    /**
     * Get detailed view of a specific workflow request
     *
     * @param requestId Workflow request ID
     * @param authentication Spring Security authentication object
     * @return Detailed workflow request with all stages and approvals
     */
    @GetMapping("/requests/{requestId}")
    public ResponseEntity<ApiResponse<WorkflowRequestDetailDTO>> getWorkflowRequestDetail(
            @PathVariable Long requestId,
            Authentication authentication) {

        log.info("Fetching workflow request detail for requestId: {}", requestId);

        String userId = authentication.getName();
        List<String> userGroups = extractUserGroups(authentication);
        System.out.println("Awaiting userId" + userId + " and userGroups" + userGroups);
        WorkflowRequestDetailDTO detail = workflowQueueService
                .getWorkflowRequestDetail(requestId, userId, userGroups);

        return ResponseEntity.ok(ApiResponse.success(detail, "Workflow request detail retrieved"));
    }

    /**
     * Get workflow request status and progress
     *
     * @param requestId Workflow request ID
     * @return Current status and stage information
     */
    @GetMapping("/requests/{requestId}/status")
    public ResponseEntity<ApiResponse<WorkflowStatusDTO>> getWorkflowStatus(
            @PathVariable Long requestId) {

        log.info("Fetching workflow status for requestId: {}", requestId);

        WorkflowStatusDTO status = workflowRequestService.getWorkflowStatus(requestId);

        return ResponseEntity.ok(ApiResponse.success(status, "Workflow status retrieved"));
    }

    /**
     * Get workflow logs for audit trail
     *
     * @param requestId Workflow request ID
     * @return List of all workflow events
     */
    @GetMapping("/requests/{requestId}/logs")
    public ResponseEntity<ApiResponse<List<WorkflowLogDTO>>> getWorkflowLogs(
            @PathVariable Long requestId) {

        log.info("Fetching workflow logs for requestId: {}", requestId);

        List<WorkflowLogDTO> logs = workflowRequestService.getWorkflowLogs(requestId);

        return ResponseEntity.ok(ApiResponse.success(logs, "Workflow logs retrieved"));
    }

    /**
     * Get summary statistics for user's workflow queue
     *
     * @param authentication Spring Security authentication object
     * @return Count of pending actions and engagements by module
     */
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<WorkflowSummaryDTO>> getWorkflowSummary(
            Authentication authentication) {

        log.info("Fetching workflow summary");

        String userId = authentication.getName();
        List<String> userGroups = extractUserGroups(authentication);
        System.out.println("Awaiting userId" + userId + " and userGroups" + userGroups);
        WorkflowSummaryDTO summary = workflowQueueService
                .getWorkflowSummary(userId, userGroups);

        return ResponseEntity.ok(ApiResponse.success(summary, "Workflow summary retrieved"));
    }

    /**
     * Bulk approve multiple stages (for efficiency)
     *
     * @param bulkDecisionDTO List of stage decisions
     * @param authentication Spring Security authentication object
     * @return Results of bulk approval
     */
    @PostMapping("/stages/bulk-decision")
    public ResponseEntity<ApiResponse<List<WorkflowDecisionResponseDTO>>> bulkProcessDecision(
            @RequestBody BulkWorkflowDecisionDTO bulkDecisionDTO,
            Authentication authentication) {

        log.info("Processing bulk decision for {} stages", bulkDecisionDTO.getDecisions().size());

        String userId = authentication.getName();
        String username = getUserDisplayName(authentication);
        List<String> userGroups = extractUserGroups(authentication);

        List<WorkflowDecisionResponseDTO> responses = new ArrayList<>();

        for (StageDecisionDTO decision : bulkDecisionDTO.getDecisions()) {
            try {
                WorkflowDecisionDTO decisionDTO = new WorkflowDecisionDTO();
                decisionDTO.setUserId(userId);
                decisionDTO.setUsername(username);
                decisionDTO.setDecision(decision.getDecision());
                decisionDTO.setRemarks(decision.getRemarks());
                decisionDTO.setUserGroup(userGroups.get(0));

                WorkflowDecisionResponseDTO response = workflowQueueService
                        .processDecision(decision.getStageId(), decisionDTO);
                responses.add(response);
            } catch (Exception e) {
                log.error("Error processing decision for stage: {}", decision.getStageId(), e);
                WorkflowDecisionResponseDTO errorResponse = new WorkflowDecisionResponseDTO();
                errorResponse.setStageId(decision.getStageId());
                errorResponse.setMessage("Error: " + e.getMessage());
                responses.add(errorResponse);
            }
        }

        return ResponseEntity.ok(ApiResponse.success(responses,
                "Processed " + responses.size() + " decisions"));
    }

    // Helper methods

    /**
     * Extract user groups/roles from Spring Security authentication
     */
    private List<String> extractUserGroups(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(auth -> auth.startsWith("ROLE_") || auth.startsWith("GROUP_"))
                .map(auth -> auth.replaceFirst("^(ROLE_|GROUP_)", ""))
                .collect(Collectors.toList());
    }

    /**
     * Get user display name from authentication
     */
    private String getUserDisplayName(Authentication authentication) {
        // This can be customized based on your authentication implementation
        if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.UserDetails) {
            return ((org.springframework.security.core.userdetails.UserDetails) authentication.getPrincipal())
                    .getUsername();
        }
        return authentication.getName();
    }
}
