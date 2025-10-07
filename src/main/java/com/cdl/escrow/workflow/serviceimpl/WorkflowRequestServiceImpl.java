package com.cdl.escrow.workflow.serviceimpl;

import com.cdl.escrow.entity.*;
import com.cdl.escrow.enumeration.WorkflowRequestStatus;
import com.cdl.escrow.enumeration.WorkflowStageStatus;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.repository.TaskStatusRepository;
import com.cdl.escrow.workflow.dto.MyEngagementDTO;
import com.cdl.escrow.workflow.dto.WorkflowRequestDTO;
import com.cdl.escrow.workflow.mapper.WorkflowRequestMapper;
import com.cdl.escrow.workflow.repository.*;
import com.cdl.escrow.workflow.service.WorkflowRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkflowRequestServiceImpl implements WorkflowRequestService {

    private final WorkflowRequestRepository repository;

    private final WorkflowRequestMapper mapper;

    private final WorkflowDefinitionRepository workflowDefinitionRepository;

    private final WorkflowStageTemplateRepository workflowStageTemplateRepository;

    private final WorkflowRequestStageRepository workflowRequestStageRepository;

    private final WorkflowRequestLogRepository workflowRequestLogRepository;

    private final TaskStatusRepository taskStatusRepository;



    @Override
    public Page<WorkflowRequestDTO> getAllWorkflowRequest(Pageable pageable) {
        log.debug("Fetching all workflow request , page: {}", pageable.getPageNumber());
        Page<WorkflowRequest> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    public Optional<WorkflowRequestDTO> getWorkflowRequestById(Long id) {
        log.debug("Fetching workflow request with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    public WorkflowRequestDTO saveWorkflowRequest(WorkflowRequestDTO workflowRequestDTO) {
        log.info("Saving new workflow request");
        WorkflowRequest entity = mapper.toEntity(workflowRequestDTO);
        WorkflowRequest saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public WorkflowRequestDTO updateWorkflowRequest(Long id, WorkflowRequestDTO workflowRequestDTO) {
        log.info("Updating workflow request with ID: {}", id);

        WorkflowRequest existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("workflow request not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        WorkflowRequest toUpdate = mapper.toEntity(workflowRequestDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        WorkflowRequest updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    public Boolean deleteWorkflowRequestById(Long id) {
        log.info("Deleting workflow request with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("workflow request not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    //  Business logic

    @Transactional
    public WorkflowRequestDTO createRequest(WorkflowRequestDTO workflowRequestDTO, String currentUser) {
        // Fetch all task statuses (lookup table)
        List<TaskStatus> taskStatusList = taskStatusRepository.findAll();

        // 1. Fetch workflow definition
        WorkflowDefinition definition = workflowDefinitionRepository.findByModuleCodeAndActionCode(
                workflowRequestDTO.getModuleName(), workflowRequestDTO.getActionKey()
        ).orElseThrow(() -> new IllegalArgumentException("No workflow definition found"));

        // 2. Create request
        WorkflowRequest request = new WorkflowRequest();
        request.setWorkflowDefinition(definition);
        request.setReferenceId(workflowRequestDTO.getReferenceId());
        request.setReferenceType(workflowRequestDTO.getReferenceType());
        request.setModuleName(workflowRequestDTO.getModuleName());
        request.setActionKey(workflowRequestDTO.getActionKey());
        request.setAmount(workflowRequestDTO.getAmount());
        request.setCurrency(workflowRequestDTO.getCurrency());
        request.setPayloadJson(workflowRequestDTO.getPayloadJson());
        request.setCreatedBy(currentUser);
        request.setCurrentStageOrder(1);

        // --- Set request status from TaskStatus list ---
        // Preferred codes - adapt these to your actual TaskStatus.code values
        // Common choices: "PENDING", "REQUEST_PENDING", "OPEN" etc.
        TaskStatus requestStatus = findTaskStatus(taskStatusList, "PENDING", "REQUEST_PENDING", "OPEN");
        if (requestStatus == null) {
            // Try by name fallback (case-insensitive)
            requestStatus = findTaskStatusByName(taskStatusList, "Pending");
        }
        // Final fallback to any available status to avoid nulls (optional)
        if (requestStatus == null && !taskStatusList.isEmpty()) {
            requestStatus = taskStatusList.get(0);
        }

        request.setTaskStatus(requestStatus);

        // Persist request (so stages can refer to it)
        repository.save(request);

        // 3. Generate stages
        List<WorkflowStageTemplate> templates =
                workflowStageTemplateRepository.findByWorkflowDefinitionId(definition.getId());

        // Preferred stage status codes (first stage vs others)
        TaskStatus firstStageStatus = findTaskStatus(taskStatusList, "PENDING", "READY", "OPEN");
        TaskStatus otherStageStatus = findTaskStatus(taskStatusList, "IN_PROGRESS", "NOT_STARTED", "INACTIVE");

        // Fallbacks
        if (firstStageStatus == null) firstStageStatus = requestStatus;
        if (otherStageStatus == null) otherStageStatus = requestStatus;

        int order = 1;
        for (WorkflowStageTemplate template : templates) {
            WorkflowRequestStage stage = new WorkflowRequestStage();
            stage.setWorkflowRequest(request);
            stage.setStageOrder(order++);
            stage.setStageKey(template.getStageKey());
            stage.setRequiredApprovals(template.getRequiredApprovals());
            stage.setKeycloakGroup(template.getKeycloakGroup());
            stage.setApprovalsObtained(0);
            stage.setStartedAt(ZonedDateTime.now());
            stage.setVersion(1L);
            // Set status: first stage gets 'firstStageStatus' otherwise 'otherStageStatus'
            if (stage.getStageOrder() == 1) {
                stage.setTaskStatus(firstStageStatus);
            } else {
                stage.setTaskStatus(firstStageStatus);
            }

            workflowRequestStageRepository.save(stage);
        }

        // 4. Log request creation
        WorkflowRequestLog log = new WorkflowRequestLog();
        log.setWorkflowRequest(request);
        log.setEventType("REQUEST_CREATED");
        log.setEventByUser(currentUser);
        log.setEventAt(ZonedDateTime.now());
        log.setDetailsJson(workflowRequestDTO.getPayloadJson());
        workflowRequestLogRepository.save(log);

        return mapper.toDto(request);
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
