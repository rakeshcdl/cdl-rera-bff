package com.cdl.escrow.workflow.controller;

import com.cdl.escrow.entity.ApplicationModule;
import com.cdl.escrow.entity.WorkflowAction;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.workflow.dto.WorkflowDefinitionDTO;
import com.cdl.escrow.workflow.repository.WorkflowDefinitionRepository;
import com.cdl.escrow.workflow.service.WorkflowDefinitionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/workflow-definition")
@RequiredArgsConstructor
@Slf4j
public class WorkflowDefinitionController {

    private final WorkflowDefinitionService workflowDefinitionService;

    private final WorkflowDefinitionRepository repository;

    private static final String ENTITY_NAME = "WORKFLOW-DEFINITION";

    @GetMapping
    public ResponseEntity<List<WorkflowDefinitionDTO>> getAllWorkflowDefinitions() {
        return  null;
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<WorkflowDefinitionDTO>> getAllWorkflowDefinitions(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all workflow definition, page: {}", pageable.getPageNumber());
        Page<WorkflowDefinitionDTO> page = workflowDefinitionService.getAllWorkflowDefinition(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<WorkflowDefinitionDTO> saveWorkflowDefinition(
            @Valid @RequestBody WorkflowDefinitionDTO dto) {
        log.info("Creating new workflow definition");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new workflow definition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkflowDefinitionDTO saved = workflowDefinitionService.saveWorkflowDefinition(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowDefinitionDTO> getWorkflowDefinitionById(@PathVariable Long id) {
        return workflowDefinitionService.getWorkflowDefinitionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("workflow definition not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowDefinitionDTO> updateWorkflowDefinition(
            @PathVariable Long id,
            @Valid @RequestBody WorkflowDefinitionDTO dto) {
        log.info("Updating workflow definition with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        WorkflowDefinitionDTO updated = workflowDefinitionService.updateWorkflowDefinition(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkflowDefinitionById(@PathVariable Long id) {
        log.info("Deleting workflow definition with ID: {}", id);
        boolean deleted = workflowDefinitionService.deleteWorkflowDefinitionById(id);
        if (deleted) {
            return ResponseEntity.ok("workflow definition deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("workflow definition deletion failed - ID: " + id);
        }
    }

    // Business logic for search

    @GetMapping("/search")
    public ResponseEntity<Page<WorkflowDefinitionDTO>> searchDefinitions(
            @RequestParam(required = false) String moduleCode,
            @RequestParam(required = false) String actionCode,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
        return ResponseEntity.ok(workflowDefinitionService.search(moduleCode, actionCode, keyword, pageable));
    }

    private List<Sort.Order> parseSort(String[] sortParams) {
        List<Sort.Order> orders = new ArrayList<>();
        for (String param : sortParams) {
            String[] parts = param.split(",");
            if (parts.length == 2) {
                orders.add(new Sort.Order(Sort.Direction.fromString(parts[1]), parts[0]));
            } else {
                orders.add(new Sort.Order(Sort.Direction.ASC, parts[0]));
            }
        }
        return orders;
    }

    @PostMapping("/create")
    public ResponseEntity<WorkflowDefinitionDTO> create(@RequestBody WorkflowDefinitionDTO dto) {
        return ResponseEntity.ok(workflowDefinitionService.create(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<WorkflowDefinitionDTO> update(
            @PathVariable Long id,
            @RequestBody WorkflowDefinitionDTO dto) {
        return ResponseEntity.ok(workflowDefinitionService.update(id, dto));
    }

    @GetMapping("/advance-search")
    public ResponseEntity<Page<WorkflowDefinitionDTO>> search(
            @RequestParam(required = false) ApplicationModule module,
            @RequestParam(required = false) WorkflowAction action,
            @RequestParam(defaultValue = "true") boolean active,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(workflowDefinitionService.advanceSearch(module, action, active, pageable));
    }
}
