package com.cdl.escrow.workflow.controller;

import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.workflow.criteria.WorkflowRequestCriteria;
import com.cdl.escrow.workflow.criteria.WorkflowRequestLogCriteria;
import com.cdl.escrow.workflow.criteriaservice.WorkflowRequestCriteriaService;
import com.cdl.escrow.workflow.criteriaservice.WorkflowRequestLogCriteriaService;
import com.cdl.escrow.workflow.dto.WorkflowRequestDTO;
import com.cdl.escrow.workflow.dto.WorkflowRequestLogDTO;
import com.cdl.escrow.workflow.repository.WorkflowRequestLogRepository;
import com.cdl.escrow.workflow.service.WorkflowRequestLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/workflow-request-log")
@RequiredArgsConstructor
@Slf4j
public class WorkflowRequestLogController {

    private final WorkflowRequestLogService workflowRequestLogService;

    private final WorkflowRequestLogRepository repository;

    private static final String ENTITY_NAME = "WORKFLOW-REQUEST-LOG";

    private final WorkflowRequestLogCriteriaService workflowRequestLogCriteriaService;


    @GetMapping
    public ResponseEntity<Page<WorkflowRequestLogDTO>> getAllWorkflowRequestLogs(@ParameterObject WorkflowRequestLogCriteria criteria,
                                                                           @ParameterObject  Pageable pageable) {
        Page<WorkflowRequestLogDTO> page = workflowRequestLogCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<WorkflowRequestLogDTO>> getAllWorkflowRequestLogs(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all workflow request log, page: {}", pageable.getPageNumber());
        Page<WorkflowRequestLogDTO> page = workflowRequestLogService.getAllWorkflowRequestLog(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<WorkflowRequestLogDTO> saveWorkflowRequestLog(
            @Valid @RequestBody WorkflowRequestLogDTO dto) {
        log.info("Creating new workflow request log");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new workflow request log cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkflowRequestLogDTO saved = workflowRequestLogService.saveWorkflowRequestLog(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowRequestLogDTO> getWorkflowRequestLogById(@PathVariable Long id) {
        return workflowRequestLogService.getWorkflowRequestLogById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("workflow request log not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowRequestLogDTO> updateWorkflowRequestLog(
            @PathVariable Long id,
            @Valid @RequestBody WorkflowRequestLogDTO dto) {
        log.info("Updating workflow request log with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        WorkflowRequestLogDTO updated = workflowRequestLogService.updateWorkflowRequestLog(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkflowDefinitionById(@PathVariable Long id) {
        log.info("Deleting workflow request log with ID: {}", id);
        boolean deleted = workflowRequestLogService.deleteWorkflowRequestLogById(id);
        if (deleted) {
            return ResponseEntity.ok("workflow request log deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("workflow request log deletion failed - ID: " + id);
        }
    }
}
