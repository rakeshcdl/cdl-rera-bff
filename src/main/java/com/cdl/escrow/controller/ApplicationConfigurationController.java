package com.cdl.escrow.controller;
import com.cdl.escrow.criteria.ApplicationConfigurationCriteria;
import com.cdl.escrow.criteriaservice.ApplicationConfigurationCriteriaService;
import com.cdl.escrow.dto.ApplicationConfigurationDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.ApplicationConfigurationRepository;
import com.cdl.escrow.service.ApplicationConfigurationService;
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
@RequestMapping("/api/v1/application-configuration")
@RequiredArgsConstructor
@Slf4j
public class ApplicationConfigurationController {
    private final ApplicationConfigurationService applicationConfigurationService;

    private final ApplicationConfigurationCriteriaService applicationConfigurationCriteriaService;

    private final ApplicationConfigurationRepository repository;

    private static final String ENTITY_NAME = "APPLICATION_CONFIGURATION";

    @GetMapping
    public ResponseEntity<Page<ApplicationConfigurationDTO>> getAllApplicationConfigurationsByCriteria(@ParameterObject ApplicationConfigurationCriteria criteria,
                                                                                           @ParameterObject  Pageable pageable) {
        Page<ApplicationConfigurationDTO> page = applicationConfigurationCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

   @GetMapping("/find-all")
    public ResponseEntity<Page<ApplicationConfigurationDTO>> getAllApplicationConfigurations(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all application configurations, page: {}", pageable.getPageNumber());
        Page<ApplicationConfigurationDTO> page = applicationConfigurationService.getAllApplicationConfiguration(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<ApplicationConfigurationDTO> saveApplicationConfiguration(
            @Valid @RequestBody ApplicationConfigurationDTO dto) {
        log.info("Creating new application configuration");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new application config cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationConfigurationDTO saved = applicationConfigurationService.saveApplicationConfiguration(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationConfigurationDTO> getApplicationConfigurationById(@PathVariable Long id) {
        log.info("Fetching application configuration with ID: {}", id);
        return applicationConfigurationService.getApplicationConfigurationById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Application configuration not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationConfigurationDTO> updateApplicationConfiguration(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationConfigurationDTO dto) {
        log.info("Updating application configuration with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        ApplicationConfigurationDTO updated = applicationConfigurationService.updateApplicationConfiguration(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplicationConfigurationById(@PathVariable Long id) {
        log.info("Deleting application configuration with ID: {}", id);
        boolean deleted = applicationConfigurationService.deleteApplicationConfigurationById(id);
        if (deleted) {
            return ResponseEntity.ok("ApplicationConfiguration deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("ApplicationConfiguration deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteApplicationConfigurationById(@PathVariable Long id) {
        log.info("Soft deleting ApplicationConfiguration with ID: {}", id);

        boolean deleted = applicationConfigurationService.softDeleteApplicationConfigurationById(id);
        if (deleted) {
            return ResponseEntity.ok("ApplicationConfiguration soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("ApplicationConfiguration soft deletion failed - ID: " + id);
        }
    }
}
