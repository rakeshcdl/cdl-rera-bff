package com.cdl.escrow.workflow.controller;

import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.workflow.dto.WorkflowAmountStageOverrideDTO;
import com.cdl.escrow.workflow.repository.WorkflowAmountStageOverrideRepository;
import com.cdl.escrow.workflow.service.WorkflowAmountStageOverrideService;
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
@RequestMapping("/api/v1/workflow-amount-stage-override")
@RequiredArgsConstructor
@Slf4j
public class WorkflowAmountStageOverrideController {

    private final WorkflowAmountStageOverrideService workflowAmountStageOverrideService;

    private final WorkflowAmountStageOverrideRepository repository;

    private static final String ENTITY_NAME = "WORKFLOW-AMOUNT-STAGE-OVERRIDE";

    @GetMapping
    public ResponseEntity<List<WorkflowAmountStageOverrideDTO>> getAllWorkflowAmountStageOverrides() {
        return  null;
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<WorkflowAmountStageOverrideDTO>> getAllWorkflowAmountStageOverrides(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all workflow amount stage override, page: {}", pageable.getPageNumber());
        Page<WorkflowAmountStageOverrideDTO> page = workflowAmountStageOverrideService.getAllWorkflowAmountStageOverride(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<WorkflowAmountStageOverrideDTO> saveWorkflowAmountStageOverride(
            @Valid @RequestBody WorkflowAmountStageOverrideDTO dto) {
        log.info("Creating new workflow amount stage override");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new workflow amount stage override cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkflowAmountStageOverrideDTO saved = workflowAmountStageOverrideService.saveWorkflowAmountStageOverride(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowAmountStageOverrideDTO> getWorkflowAmountStageOverrideById(@PathVariable Long id) {
        log.info("Fetching workflow amount stage override with ID: {}", id);
        return workflowAmountStageOverrideService.getWorkflowAmountStageOverrideById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("workflow amount stage override not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowAmountStageOverrideDTO> updateWorkflowAmountStageOverride(
            @PathVariable Long id,
            @Valid @RequestBody WorkflowAmountStageOverrideDTO dto) {
        log.info("Updating workflow amount stage override with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        WorkflowAmountStageOverrideDTO updated = workflowAmountStageOverrideService.updateWorkflowAmountStageOverride(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkflowAmountStageOverrideById(@PathVariable Long id) {
        log.info("Deleting workflow amount stage override with ID: {}", id);
        boolean deleted = workflowAmountStageOverrideService.deleteWorkflowAmountStageOverrideById(id);
        if (deleted) {
            return ResponseEntity.ok("workflow amount stage override deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("workflow amount stage override deletion failed - ID: " + id);
        }
    }
}
