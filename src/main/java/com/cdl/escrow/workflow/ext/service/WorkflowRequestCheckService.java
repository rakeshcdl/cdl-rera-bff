package com.cdl.escrow.workflow.ext.service;

import com.cdl.escrow.entity.*;
import com.cdl.escrow.repository.TaskStatusRepository;
import com.cdl.escrow.workflow.dto.WorkflowRequestDTO;
import com.cdl.escrow.workflow.ext.dto.WorkflowLogDTO;
import com.cdl.escrow.workflow.ext.dto.WorkflowRequestResponseDTO;
import com.cdl.escrow.workflow.ext.dto.WorkflowStageDTO;
import com.cdl.escrow.workflow.ext.dto.WorkflowStatusDTO;
import com.cdl.escrow.workflow.ext.exception.ResourceNotFoundException;
import com.cdl.escrow.workflow.ext.exception.WorkflowConfigurationException;
import com.cdl.escrow.workflow.repository.WorkflowDefinitionRepository;
import com.cdl.escrow.workflow.repository.WorkflowRequestLogRepository;
import com.cdl.escrow.workflow.repository.WorkflowRequestRepository;
import com.cdl.escrow.workflow.repository.WorkflowRequestStageRepository;
import com.cdl.escrow.workflow.service.WorkflowAmountRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class WorkflowRequestCheckService {

    private final WorkflowRequestRepository workflowRequestRepository;
    private final WorkflowDefinitionRepository workflowDefinitionRepository;
    private final WorkflowRequestStageRepository workflowRequestStageRepository;
    private final WorkflowRequestLogRepository workflowLogRepository;
    private final WorkflowAmountRuleService amountRuleService;
    private final TaskStatusRepository taskStatusRepository;

    /**
     * Create a new workflow request
     * This initializes the workflow with all stages based on workflow definition
     */
    @Transactional
    public WorkflowRequestResponseDTO createWorkflowRequest(WorkflowRequestDTO requestDTO) {
        log.info("Creating workflow request for module: {}, action: {}, amount: {}",
                requestDTO.getModuleName(), requestDTO.getActionKey(), requestDTO.getAmount());

        // Find active workflow definition
        WorkflowDefinition workflowDefinition = workflowDefinitionRepository
                .findByModuleCodeAndActionCode(requestDTO.getModuleName(), requestDTO.getActionKey())
                .orElseThrow(() -> new WorkflowConfigurationException(
                        "No active workflow found for module: " + requestDTO.getModuleName() +
                                ", action: " + requestDTO.getActionKey()));

        // Load stages and rules
        workflowDefinition = workflowDefinitionRepository
                .findByIdWithStagesAndRules(workflowDefinition.getId())
                .orElseThrow(() -> new WorkflowConfigurationException("Workflow definition not found"));

        // Get PENDING status
        TaskStatus pendingStatus = taskStatusRepository.findByCode("PENDING")
                .orElseThrow(() -> new ResourceNotFoundException("PENDING status not found"));

        // Create workflow request
        WorkflowRequest workflowRequest = new WorkflowRequest();
        workflowRequest.setWorkflowDefinition(workflowDefinition);
        workflowRequest.setReferenceId(requestDTO.getReferenceId());
        workflowRequest.setReferenceType(requestDTO.getReferenceType());
        workflowRequest.setModuleName(requestDTO.getModuleName());
        workflowRequest.setActionKey(requestDTO.getActionKey());
        workflowRequest.setAmount(requestDTO.getAmount());
        workflowRequest.setCurrency(requestDTO.getCurrency());
        workflowRequest.setPayloadJson(requestDTO.getPayloadJson());
        workflowRequest.setCurrentStageOrder(1);
        workflowRequest.setCreatedBy(requestDTO.getCreatedBy());
        workflowRequest.setTaskStatus(pendingStatus);
        workflowRequest.setVersion(1L);
        workflowRequest.setEnabled(true);
        workflowRequest.setDeleted(false);

        workflowRequest = workflowRequestRepository.save(workflowRequest);

        // Create workflow stages based on definition
        List<WorkflowRequestStage> stages = createWorkflowStages(
                workflowRequest,
                workflowDefinition,
                requestDTO.getAmount()
        );

        // Save all stages
        workflowRequestStageRepository.saveAll(stages);

        // Start first stage
        if (!stages.isEmpty()) {
            WorkflowRequestStage firstStage = stages.get(0);
            firstStage.setStartedAt(ZonedDateTime.now());
            firstStage.setTaskStatus(pendingStatus);
            workflowRequestStageRepository.save(firstStage);

            // Log workflow creation
            logWorkflowEvent(workflowRequest, "WORKFLOW_CREATED",
                    requestDTO.getCreatedBy(), null,
                    Map.of(
                            "moduleName", requestDTO.getModuleName(),
                            "actionKey", requestDTO.getActionKey(),
                            "totalStages", stages.size()
                    ));

            // Log first stage start
            logWorkflowEvent(workflowRequest, "STAGE_STARTED", null, null,
                    Map.of(
                            "stageOrder", firstStage.getStageOrder(),
                            "stageKey", firstStage.getStageKey(),
                            "group", firstStage.getKeycloakGroup()
                    ));
        }

        log.info("Workflow request created with ID: {} and {} stages",
                workflowRequest.getId(), stages.size());

        // Build response
        return buildWorkflowRequestResponse(workflowRequest, stages);
    }

    /**
     * Create workflow stages based on definition and amount rules
     */
    private List<WorkflowRequestStage> createWorkflowStages(
            WorkflowRequest workflowRequest,
            WorkflowDefinition workflowDefinition,
            BigDecimal amount) {

        List<WorkflowRequestStage> stages = new ArrayList<>();
        TaskStatus notStartedStatus = taskStatusRepository.findByCode("NOT_STARTED")
                .orElseThrow(() -> new ResourceNotFoundException("NOT_STARTED status not found"));

        // Check if workflow is amount-based
        if (Boolean.TRUE.equals(workflowDefinition.getAmountBased()) && amount != null) {
            // Apply amount rules
            List<WorkflowStageTemplate> templates = amountRuleService
                    .getStagesForAmount(workflowDefinition, amount);

            int order = 1;
            for (WorkflowStageTemplate template : templates) {
                WorkflowRequestStage stage = new WorkflowRequestStage();
                stage.setWorkflowRequest(workflowRequest);
                stage.setStageOrder(order++);
                stage.setStageKey(template.getStageKey());
                stage.setKeycloakGroup(template.getKeycloakGroup());
                stage.setRequiredApprovals(template.getRequiredApprovals());
                stage.setApprovalsObtained(0);
                stage.setTaskStatus(notStartedStatus);
                stage.setVersion(1L);
                stage.setEnabled(true);
                stage.setDeleted(false);

                stages.add(stage);
            }
        } else {
            // Use default stage templates
            List<WorkflowStageTemplate> templates = workflowDefinition.getWorkflowStageTemplates()
                    .stream()
                    .filter(t -> Boolean.TRUE.equals(t.getEnabled()) && Boolean.FALSE.equals(t.getDeleted()))
                    .sorted((a, b) -> a.getStageOrder().compareTo(b.getStageOrder()))
                    .collect(Collectors.toList());

            for (WorkflowStageTemplate template : templates) {
                WorkflowRequestStage stage = new WorkflowRequestStage();
                stage.setWorkflowRequest(workflowRequest);
                stage.setStageOrder(template.getStageOrder());
                stage.setStageKey(template.getStageKey());
                stage.setKeycloakGroup(template.getKeycloakGroup());
                stage.setRequiredApprovals(template.getRequiredApprovals());
                stage.setApprovalsObtained(0);
                stage.setTaskStatus(notStartedStatus);
                stage.setVersion(1L);
                stage.setEnabled(true);
                stage.setDeleted(false);

                stages.add(stage);
            }
        }

        return stages;
    }

    /**
     * Get workflow request status
     */
    @Transactional(readOnly = true)
    public WorkflowStatusDTO getWorkflowStatus(Long requestId) {
        log.info("Getting workflow status for requestId: {}", requestId);

        WorkflowRequest request = workflowRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Workflow request not found: " + requestId));

        List<WorkflowRequestStage> stages = workflowRequestStageRepository
                .findByWorkflowRequest_Id(requestId);

        WorkflowStatusDTO statusDTO = new WorkflowStatusDTO();
        statusDTO.setRequestId(request.getId());
        statusDTO.setStatus(request.getTaskStatus().getCode());
        statusDTO.setTotalStages(stages.size());

        long completedStages = stages.stream()
                .filter(s -> "COMPLETED".equals(s.getTaskStatus().getCode()))
                .count();
        statusDTO.setCompletedStages((int) completedStages);

        // Get current stage name
        stages.stream()
                .filter(s -> s.getStageOrder().equals(request.getCurrentStageOrder()))
                .findFirst()
                .ifPresent(stage -> statusDTO.setCurrentStage(getStageName(stage.getStageKey())));

        // Map stage history
        List<WorkflowStageDTO> stageHistory = stages.stream()
                .map(this::mapToStageDTO)
                .collect(Collectors.toList());
        statusDTO.setStageHistory(stageHistory);

        return statusDTO;
    }

    /**
     * Get workflow logs
     */
    @Transactional(readOnly = true)
    public List<WorkflowLogDTO> getWorkflowLogs(Long requestId) {
        log.info("Getting workflow logs for requestId: {}", requestId);

        List<WorkflowRequestLog> logs = workflowLogRepository.findByWorkflowRequest_Id(requestId);

        return logs.stream()
                .map(this::mapToLogDTO)
                .collect(Collectors.toList());
    }

    // Helper methods

    private WorkflowRequestResponseDTO buildWorkflowRequestResponse(
            WorkflowRequest request,
            List<WorkflowRequestStage> stages) {

        WorkflowRequestResponseDTO dto = new WorkflowRequestResponseDTO();
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

        List<WorkflowStageDTO> stageDTOs = stages.stream()
                .map(this::mapToStageDTO)
                .collect(Collectors.toList());
        dto.setStages(stageDTOs);

        // Set current stage
        stages.stream()
                .filter(s -> s.getStageOrder().equals(request.getCurrentStageOrder()))
                .findFirst()
                .ifPresent(stage -> dto.setCurrentStage(mapToStageDTO(stage)));

        return dto;
    }

    private WorkflowStageDTO mapToStageDTO(WorkflowRequestStage stage) {
        WorkflowStageDTO dto = new WorkflowStageDTO();
        dto.setId(stage.getId());
        dto.setStageOrder(stage.getStageOrder());
        dto.setStageKey(stage.getStageKey());
        dto.setKeycloakGroup(stage.getKeycloakGroup());
        dto.setRequiredApprovals(stage.getRequiredApprovals());
        dto.setApprovalsObtained(stage.getApprovalsObtained());
        dto.setStatus(stage.getTaskStatus().getCode());
        dto.setStartedAt(stage.getStartedAt());
        dto.setCompletedAt(stage.getCompletedAt());
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

    private String getStageName(String stageKey) {
        Map<String, String> stageNames = Map.of(
                "MAKER", "Maker Review",
                "CHECKER", "Checker Review",
                "REVIEWER", "Reviewer Assessment",
                "ADMIN", "Admin Approval"
        );
        return stageNames.getOrDefault(stageKey, stageKey);
    }
}
