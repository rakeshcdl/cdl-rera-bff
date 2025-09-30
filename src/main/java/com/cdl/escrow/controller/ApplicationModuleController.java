package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.ApplicationModuleCriteria;
import com.cdl.escrow.criteriaservice.ApplicationModuleCriteriaService;
import com.cdl.escrow.dto.ApplicationModuleDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.ApplicationModuleRepository;
import com.cdl.escrow.service.ApplicationModuleService;
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
@RequestMapping("/api/v1/application-module")
@RequiredArgsConstructor
@Slf4j
public class ApplicationModuleController {
    private final ApplicationModuleService applicationModuleService;

    private final ApplicationModuleCriteriaService applicationModuleCriteriaService;

    private final ApplicationModuleRepository repository;

    private static final String ENTITY_NAME = "APPLICATION_MODULE";

    @GetMapping
    public ResponseEntity<Page<ApplicationModuleDTO>> getAllApplicationModulesByCriteria(@ParameterObject ApplicationModuleCriteria criteria,
                                                                                                 @ParameterObject  Pageable pageable) {
        Page<ApplicationModuleDTO> page = applicationModuleCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<ApplicationModuleDTO>> getAllApplicationModules(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all application module , page: {}", pageable.getPageNumber());
        Page<ApplicationModuleDTO> page = applicationModuleService.getAllApplicationModule(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<ApplicationModuleDTO> saveApplicationModule(
            @Valid @RequestBody ApplicationModuleDTO dto) {
        log.info("Creating new application module");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new application module cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationModuleDTO saved = applicationModuleService.saveApplicationModule(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationModuleDTO> getApplicationModuleById(@PathVariable Long id) {
        log.info("Fetching application module with ID: {}", id);
        return applicationModuleService.getApplicationModuleById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Application module not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationModuleDTO> updateApplicationModule(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationModuleDTO dto) {
        log.info("Updating application module with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        ApplicationModuleDTO updated = applicationModuleService.updateApplicationModule(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplicationModuleById(@PathVariable Long id) {
        log.info("Deleting application module with ID: {}", id);
        boolean deleted = applicationModuleService.deleteApplicationModuleById(id);
        if (deleted) {
            return ResponseEntity.ok("ApplicationModule deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("ApplicationModule deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteApplicationModuleById(@PathVariable Long id) {
        log.info("Soft deleting ApplicationModule with ID: {}", id);

        boolean deleted = applicationModuleService.softDeleteApplicationModuleById(id);
        if (deleted) {
            return ResponseEntity.ok("ApplicationModule soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("ApplicationModule soft deletion failed - ID: " + id);
        }
    }
}
