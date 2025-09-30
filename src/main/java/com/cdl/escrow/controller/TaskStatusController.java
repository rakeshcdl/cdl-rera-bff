package com.cdl.escrow.controller;

import com.cdl.escrow.dto.TaskStatusDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.repository.TaskStatusRepository;
import com.cdl.escrow.service.TaskStatusService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/task-status")
@RequiredArgsConstructor
@Slf4j
public class TaskStatusController {

    private final TaskStatusService taskStatusService;

    private final TaskStatusRepository repository;

    private static final String ENTITY_NAME = "TASK_STATUS";

    @GetMapping("/find-all")
    public ResponseEntity<Page<TaskStatusDTO>> getAllTaskStatus(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all surety bond type, page: {}", pageable.getPageNumber());
        Page<TaskStatusDTO> page = taskStatusService.getAllTaskStatus(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<TaskStatusDTO> saveTaskStatus(
            @Valid @RequestBody TaskStatusDTO dto) {
        log.info("Creating new surety bond type");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new surety bond type cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskStatusDTO saved = taskStatusService.saveTaskStatus(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskStatusDTO> getTaskStatusById(@PathVariable Long id) {
        log.info("Fetching surety bond type with ID: {}", id);
        return taskStatusService.getTaskStatusyId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Surety bond type not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskStatusDTO> updateTaskStatus(
            @PathVariable Long id,
            @Valid @RequestBody TaskStatusDTO dto) {
        log.info("Updating surety bond type with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        TaskStatusDTO updated = taskStatusService.updateTaskStatus(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTaskStatusById(@PathVariable Long id) {
        log.info("Deleting surety bond type with ID: {}", id);
        boolean deleted = taskStatusService.deleteTaskStatusById(id);
        if (deleted) {
            return ResponseEntity.ok("SuretyBondType deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("SuretyBondType deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteTaskStatusServiceById(@PathVariable Long id) {
        log.info("Soft deleting SuretyBondType with ID: {}", id);

        boolean deleted = taskStatusService.softTaskStatusServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("SuretyBondType soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("SuretyBondType soft deletion failed - ID: " + id);
        }
    }
}
