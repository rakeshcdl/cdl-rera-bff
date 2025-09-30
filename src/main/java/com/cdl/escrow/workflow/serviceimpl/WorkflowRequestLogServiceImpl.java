package com.cdl.escrow.workflow.serviceimpl;

import com.cdl.escrow.entity.WorkflowRequest;
import com.cdl.escrow.entity.WorkflowRequestLog;
import com.cdl.escrow.entity.WorkflowRequestStage;
import com.cdl.escrow.enumeration.WorkflowDecision;
import com.cdl.escrow.exception.ApplicationConfigurationNotFoundException;
import com.cdl.escrow.workflow.dto.WorkflowRequestLogDTO;
import com.cdl.escrow.workflow.mapper.WorkflowRequestLogMapper;
import com.cdl.escrow.workflow.repository.WorkflowRequestLogRepository;
import com.cdl.escrow.workflow.service.WorkflowRequestLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkflowRequestLogServiceImpl implements WorkflowRequestLogService {

    private final WorkflowRequestLogRepository repository;

    private final WorkflowRequestLogMapper mapper;

    private final ObjectMapper objectMapper;

    @Override
    public Page<WorkflowRequestLogDTO> getAllWorkflowRequestLog(Pageable pageable) {
        log.debug("Fetching all workflow request log, page: {}", pageable.getPageNumber());
        Page<WorkflowRequestLog> page = repository.findAll(pageable);
        return new PageImpl<>(
                page.map(mapper::toDto).getContent(),
                pageable,
                page.getTotalElements()
        );
    }

    @Override
    public Optional<WorkflowRequestLogDTO> getWorkflowRequestLogById(Long id) {
        log.debug("Fetching workflow request log with ID: {}", id);
        return repository.findById(id)
                .map(mapper::toDto);
    }

    @Override
    public WorkflowRequestLogDTO saveWorkflowRequestLog(WorkflowRequestLogDTO workflowRequestLogDTO) {
        log.info("Saving new workflow request log");
        WorkflowRequestLog entity = mapper.toEntity(workflowRequestLogDTO);
        WorkflowRequestLog saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public WorkflowRequestLogDTO updateWorkflowRequestLog(Long id, WorkflowRequestLogDTO workflowRequestLogDTO) {
        log.info("Updating workflow request log with ID: {}", id);

        WorkflowRequestLog existing = repository.findById(id)
                .orElseThrow(() -> new ApplicationConfigurationNotFoundException("workflow request log not found with ID: " + id));

        // Optionally, update only mutable fields instead of full replacement
        WorkflowRequestLog toUpdate = mapper.toEntity(workflowRequestLogDTO);
        toUpdate.setId(existing.getId()); // Ensure the correct ID is preserved

        WorkflowRequestLog updated = repository.save(toUpdate);
        return mapper.toDto(updated);
    }

    @Override
    public Boolean deleteWorkflowRequestLogById(Long id) {
        log.info("Deleting workflow request log with ID: {}", id);

        if (!repository.existsById(id)) {
            throw new ApplicationConfigurationNotFoundException("workflow request log not found with ID: " + id);
        }

        repository.deleteById(id);
        return true;
    }

    @Override
    public void logDecision(WorkflowRequest request,
                            WorkflowRequestStage stage,
                            String userId,
                            WorkflowDecision decision,
                            String remarks) {
        Map<String, Object> details = new HashMap<>();
        details.put("stageId", stage.getId());
        details.put("stageOrder", stage.getStageOrder());
        details.put("decision", decision.name());
        details.put("remarks", remarks);

        saveLog(request, "DECISION", userId, stage.getKeycloakGroup(), details);
    }

    @Override
    public void logStageEvent(WorkflowRequest request,
                              WorkflowRequestStage stage,
                              String eventType,
                              Object details) {
        saveLog(request, eventType, null, stage.getKeycloakGroup(), details);
    }

    @Override
    public void logWorkflowEvent(WorkflowRequest request,
                                 String eventType,
                                 Object details) {
        saveLog(request, eventType, null, null, details);
    }

    private void saveLog(WorkflowRequest request,
                         String eventType,
                         String eventByUser,
                         String eventByGroup,
                         Object details) {
        WorkflowRequestLog log = new WorkflowRequestLog();
        log.setWorkflowRequest(request);
        log.setEventType(eventType);
        log.setEventByUser(eventByUser);
        log.setEventByGroup(eventByGroup);

        try {
           // log.setDetailsJson(objectMapper.writeValueAsString(details));
            log.setDetailsJson((Map<String, Object>) details);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize log details", e);
        }

        repository.save(log);
    }
}
