package com.cdl.escrow.workflow.controller;

import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.workflow.dto.WorkflowStageTemplateDTO;
import com.cdl.escrow.workflow.repository.WorkflowStageTemplateRepository;
import com.cdl.escrow.workflow.service.WorkflowStageTemplateService;
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
@RequestMapping("/api/v1/workflow-stage-template")
@RequiredArgsConstructor
@Slf4j
public class WorkflowStageTemplateController {

    private final WorkflowStageTemplateService workflowStageTemplateService;

    private final WorkflowStageTemplateRepository repository;

    private static final String ENTITY_NAME = "WORKFLOW-STAGE-TEMPLATE";

    @GetMapping
    public ResponseEntity<List<WorkflowStageTemplateDTO>> getAllWorkflowStageTemplates() {
        return  null;
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<WorkflowStageTemplateDTO>> getAllWorkflowStageTemplates(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all workflow request stage template, page: {}", pageable.getPageNumber());
        Page<WorkflowStageTemplateDTO> page = workflowStageTemplateService.getAllWorkflowStageTemplate(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<WorkflowStageTemplateDTO> saveWorkflowStageTemplate(
            @Valid @RequestBody WorkflowStageTemplateDTO dto) {
        log.info("Creating new workflow request stage template");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new workflow request stage template cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkflowStageTemplateDTO saved = workflowStageTemplateService.saveWorkflowStageTemplate(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowStageTemplateDTO> getWorkflowStageTemplateById(@PathVariable Long id) {
        return workflowStageTemplateService.getWorkflowStageTemplateById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("workflow request stage template not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowStageTemplateDTO> updateWorkflowStageTemplate(
            @PathVariable Long id,
            @Valid @RequestBody WorkflowStageTemplateDTO dto) {
        log.info("Updating workflow request stage template with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        WorkflowStageTemplateDTO updated = workflowStageTemplateService.updateWorkflowStageTemplate(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkflowStageTemplateById(@PathVariable Long id) {
        log.info("Deleting workflow request stage template with ID: {}", id);
        boolean deleted = workflowStageTemplateService.deleteWorkflowStageTemplateById(id);
        if (deleted) {
            return ResponseEntity.ok("workflow request stage template deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("workflow request stage template deletion failed - ID: " + id);
        }
    }

    // Business specific

    @PostMapping("/reorder/{workflowId}")
    public ResponseEntity<Void> reorderStages(
            @PathVariable Long workflowId,
            @RequestBody List<Long> stageIdsInOrder) {
        workflowStageTemplateService.reorderStages(workflowId, stageIdsInOrder);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{stageId}/assign-group")
    public ResponseEntity<WorkflowStageTemplateDTO> assignGroup(
            @PathVariable Long stageId,
            @RequestParam String groupName) {
        return ResponseEntity.ok(workflowStageTemplateService.assignGroupToStage(stageId, groupName));
    }
}
