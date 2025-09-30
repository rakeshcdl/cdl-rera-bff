package com.cdl.escrow.workflow.controller;

import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.workflow.dto.WorkflowAmountRuleDTO;
import com.cdl.escrow.workflow.repository.WorkflowAmountRuleRepository;
import com.cdl.escrow.workflow.service.WorkflowAmountRuleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/workflow-amount-rule")
@RequiredArgsConstructor
@Slf4j
public class WorkflowAmountRuleController {

    private final WorkflowAmountRuleService workflowAmountRuleService;

    private final WorkflowAmountRuleRepository workflowAmountRuleRepository;

    private static final String ENTITY_NAME = "WORKFLOW-AMOUNT-RULE";

    @GetMapping
    public ResponseEntity<List<WorkflowAmountRuleDTO>> getAllWorkflowAmountRules() {
        return  null;
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<WorkflowAmountRuleDTO>> getAllWorkflowAmountRules(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all workflow amount rule, page: {}", pageable.getPageNumber());
        Page<WorkflowAmountRuleDTO> page = workflowAmountRuleService.getAllWorkflowAmountRule(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<WorkflowAmountRuleDTO> saveWorkflowAmountRule(
            @Valid @RequestBody WorkflowAmountRuleDTO dto) {
        log.info("Creating new workflow amount rule");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new workflow amount rule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkflowAmountRuleDTO saved = workflowAmountRuleService.saveWorkflowAmountRule(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowAmountRuleDTO> getWorkflowAmountRuleById(@PathVariable Long id) {
        log.info("Fetching workflow amount rule with ID: {}", id);
        return workflowAmountRuleService.getWorkflowAmountRuleById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("workflow amount rule not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowAmountRuleDTO> updateWorkflowAmountRule(
            @PathVariable Long id,
            @Valid @RequestBody WorkflowAmountRuleDTO dto) {
        log.info("Updating workflow amount rule with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workflowAmountRuleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        WorkflowAmountRuleDTO updated = workflowAmountRuleService.updateWorkflowAmountRule(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkflowAmountRuleById(@PathVariable Long id) {
        log.info("Deleting workflow amount rule with ID: {}", id);
        boolean deleted = workflowAmountRuleService.deleteWorkflowAmountRuleById(id);
        if (deleted) {
            return ResponseEntity.ok("workflow amount rule deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("workflow amount rule deletion failed - ID: " + id);
        }
    }

    // Business objects

    @GetMapping("/resolve/{workflowId}")
    public ResponseEntity<WorkflowAmountRuleDTO> resolveRule(
            @PathVariable Long workflowId,
            @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(workflowAmountRuleService.resolveRule(workflowId, amount));
    }
}
