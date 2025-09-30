package com.cdl.escrow.workflow.controller;

import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.workflow.dto.WorkflowRequestStageDTO;
import com.cdl.escrow.workflow.repository.WorkflowRequestStageRepository;
import com.cdl.escrow.workflow.service.WorkflowRequestStageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/workflow-request-stage")
@RequiredArgsConstructor
@Slf4j
public class WorkflowRequestStageController {

    private final WorkflowRequestStageService workflowRequestStageService;

    private final WorkflowRequestStageRepository repository;

    private static final String ENTITY_NAME = "WORKFLOW-REQUEST-STAGE";

    @GetMapping
    public ResponseEntity<List<WorkflowRequestStageDTO>> getAllWorkflowRequestStages() {
        return  null;
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<WorkflowRequestStageDTO>> getAllWorkflowRequestStages(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all workflow request stage, page: {}", pageable.getPageNumber());
        Page<WorkflowRequestStageDTO> page = workflowRequestStageService.getAllWorkflowRequestStage(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<WorkflowRequestStageDTO> saveWorkflowRequestStage(
            @Valid @RequestBody WorkflowRequestStageDTO dto) {
        log.info("Creating new workflow request stage");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new workflow request stage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkflowRequestStageDTO saved = workflowRequestStageService.saveWorkflowRequestStage(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowRequestStageDTO> getWorkflowRequestStageById(@PathVariable Long id) {
        return workflowRequestStageService.getWorkflowRequestStageById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("workflow request stage not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowRequestStageDTO> updateWorkflowRequestStage(
            @PathVariable Long id,
            @Valid @RequestBody WorkflowRequestStageDTO dto) {
        log.info("Updating workflow request stage with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        WorkflowRequestStageDTO updated = workflowRequestStageService.updateWorkflowRequestStage(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkflowRequestStageById(@PathVariable Long id) {
        log.info("Deleting workflow request stage with ID: {}", id);
        boolean deleted = workflowRequestStageService.deleteWorkflowRequestStageById(id);
        if (deleted) {
            return ResponseEntity.ok("workflow request stage deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("workflow request stage deletion failed - ID: " + id);
        }
    }
}
