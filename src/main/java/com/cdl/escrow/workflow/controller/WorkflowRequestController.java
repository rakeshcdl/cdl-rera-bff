package com.cdl.escrow.workflow.controller;

import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.workflow.criteria.WorkflowRequestCriteria;
import com.cdl.escrow.workflow.criteriaservice.WorkflowRequestCriteriaService;
import com.cdl.escrow.workflow.dto.WorkflowRequestDTO;
import com.cdl.escrow.workflow.repository.WorkflowRequestRepository;
import com.cdl.escrow.workflow.service.WorkflowRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/workflow-request")
@RequiredArgsConstructor
@Slf4j
public class WorkflowRequestController {

    private final WorkflowRequestService workflowRequestService;

    private final WorkflowRequestRepository repository;

    private static final String ENTITY_NAME = "WORKFLOW-REQUEST";

    private final WorkflowRequestCriteriaService workflowRequestCriteriaService;


    @GetMapping
    public ResponseEntity<Page<WorkflowRequestDTO>> getAllWorkflowRequests(@ParameterObject WorkflowRequestCriteria criteria,
                                                                               @ParameterObject  Pageable pageable) {
        Page<WorkflowRequestDTO> page = workflowRequestCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<WorkflowRequestDTO>> getAllWorkflowRequests(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all workflow request, page: {}", pageable.getPageNumber());
        Page<WorkflowRequestDTO> page = workflowRequestService.getAllWorkflowRequest(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<WorkflowRequestDTO> saveWorkflowRequest(
            @Valid @RequestBody WorkflowRequestDTO dto) {
        log.info("Creating new workflow request");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new workflow request cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkflowRequestDTO saved = workflowRequestService.saveWorkflowRequest(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkflowRequestDTO> getWorkflowRequestById(@PathVariable Long id) {

        return workflowRequestService.getWorkflowRequestById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("workflow request not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkflowRequestDTO> updateWorkflowRequest(
            @PathVariable Long id,
            @Valid @RequestBody WorkflowRequestDTO dto) {
        log.info("Updating workflow request with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        WorkflowRequestDTO updated = workflowRequestService.updateWorkflowRequest(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkflowDefinitionById(@PathVariable Long id) {
        log.info("Deleting workflow request with ID: {}", id);
        boolean deleted = workflowRequestService.deleteWorkflowRequestById(id);
        if (deleted) {
            return ResponseEntity.ok("workflow request deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("workflow request deletion failed - ID: " + id);
        }
    }

    // Business Logic

    /**
     * Create a new workflow request
     */
    @PostMapping("/create-request")
    public ResponseEntity<WorkflowRequestDTO> createRequest(
            @RequestBody WorkflowRequestDTO dto,
            Principal principal) {

        WorkflowRequestDTO request = workflowRequestService.createRequest(dto, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(request);
    }


}
