package com.cdl.escrow.workflow.controller;

import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.workflow.criteria.WorkflowRequestStageApprovalCriteria;
import com.cdl.escrow.workflow.criteriaservice.WorkflowRequestStageApprovalCriteriaService;
import com.cdl.escrow.workflow.dto.WorkflowRequestStageApprovalDTO;
import com.cdl.escrow.workflow.repository.WorkflowRequestStageApprovalRepository;
import com.cdl.escrow.workflow.service.WorkflowRequestStageApprovalService;
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
@RequestMapping("/api/v1/workflow-request-stage-approval")
@RequiredArgsConstructor
@Slf4j
public class WorkflowRequestStageApprovalController {

    private final WorkflowRequestStageApprovalService workflowRequestStageApprovalService;

    private final WorkflowRequestStageApprovalRepository repository;

    private final WorkflowRequestStageApprovalCriteriaService workflowRequestStageApprovalCriteriaService;

    private static final String ENTITY_NAME = "WORKFLOW-REQUEST-STAGE-APPROVAL";

    @GetMapping
    public ResponseEntity<List<WorkflowRequestStageApprovalDTO>> getAllWorkflowRequestStageApprovals(@ParameterObject WorkflowRequestStageApprovalCriteria criteria,
                                                                                                     @ParameterObject  Pageable pageable) {

        Page<WorkflowRequestStageApprovalDTO> page = workflowRequestStageApprovalCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<WorkflowRequestStageApprovalDTO>> getAllWorkflowRequestStageApprovals(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all workflow request stage approval, page: {}", pageable.getPageNumber());
        Page<WorkflowRequestStageApprovalDTO> page = workflowRequestStageApprovalService.getAllWorkflowRequestStageApproval(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<WorkflowRequestStageApprovalDTO> saveWorkflowRequestStageApproval(
            @Valid @RequestBody WorkflowRequestStageApprovalDTO dto) {
        log.info("Creating new workflow request stage approval");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new workflow request stage approval cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkflowRequestStageApprovalDTO saved = workflowRequestStageApprovalService.saveWorkflowRequestStageApproval(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowRequestStageApprovalDTO> getWorkflowRequestStageApprovalById(@PathVariable Long id) {
        return workflowRequestStageApprovalService.getWorkflowRequestStageApprovalById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("workflow request stage approval not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowRequestStageApprovalDTO> updateWorkflowRequestStageApproval(
            @PathVariable Long id,
            @Valid @RequestBody WorkflowRequestStageApprovalDTO dto) {
        log.info("Updating workflow request stage approval with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        WorkflowRequestStageApprovalDTO updated = workflowRequestStageApprovalService.updateWorkflowRequestStageApproval(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkflowRequestStageApprovalById(@PathVariable Long id) {
        log.info("Deleting workflow request stage approval with ID: {}", id);
        boolean deleted = workflowRequestStageApprovalService.deleteWorkflowRequestStageApprovalById(id);
        if (deleted) {
            return ResponseEntity.ok("workflow request stage approval deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("workflow request stage approval deletion failed - ID: " + id);
        }
    }
}
