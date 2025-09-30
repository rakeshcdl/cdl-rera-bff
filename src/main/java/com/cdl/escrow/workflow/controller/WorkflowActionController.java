package com.cdl.escrow.workflow.controller;

import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.workflow.dto.WorkflowActionDTO;
import com.cdl.escrow.workflow.repository.WorkflowActionRepository;
import com.cdl.escrow.workflow.service.WorkflowActionService;
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
@RequestMapping("/api/v1/workflow-action")
@RequiredArgsConstructor
@Slf4j
public class WorkflowActionController {

    private final WorkflowActionService workflowActionService;

    private final WorkflowActionRepository repository;

    private static final String ENTITY_NAME = "WORKFLOW-ACTION";

    @GetMapping
    public ResponseEntity<List<WorkflowActionDTO>> getAllWorkflowActions() {
       return  null;
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<WorkflowActionDTO>> getAllWorkflowActions(
            @ParameterObject   @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all workflow action, page: {}", pageable.getPageNumber());
        Page<WorkflowActionDTO> page = workflowActionService.getAllWorkflowAction(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<WorkflowActionDTO> saveWorkflowAction(
            @Valid @RequestBody WorkflowActionDTO dto) {
        log.info("Creating new workflow action");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new workflow action cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkflowActionDTO saved = workflowActionService.saveWorkflowAction(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowActionDTO> getWorkflowActionById(@PathVariable Long id) {
        log.info("Fetching workflow action with ID: {}", id);
        return workflowActionService.getWorkflowActionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("workflow action not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowActionDTO> updateWorkflowAction(
            @PathVariable Long id,
            @Valid @RequestBody WorkflowActionDTO dto) {
        log.info("Updating workflow action with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        WorkflowActionDTO updated = workflowActionService.updateWorkflowAction(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkflowActionById(@PathVariable Long id) {
        log.info("Deleting workflow action with ID: {}", id);
        boolean deleted = workflowActionService.deleteWorkflowActionById(id);
        if (deleted) {
            return ResponseEntity.ok("workflow action deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("workflow action deletion failed - ID: " + id);
        }
    }

    // Business logic

    @GetMapping("/search")
    public ResponseEntity<Page<WorkflowActionDTO>> searchActions(
            @RequestParam(required = false) String moduleCode,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(parseSort(sort)));
        return ResponseEntity.ok(workflowActionService.search(moduleCode, keyword, pageable));
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


}
