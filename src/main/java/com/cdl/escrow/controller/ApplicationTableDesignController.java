package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.ApplicationTableDesignCriteria;
import com.cdl.escrow.criteriaservice.ApplicationTableDesignCriteriaService;
import com.cdl.escrow.dto.ApplicationTableDesignDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.ApplicationTableDesignRepository;
import com.cdl.escrow.service.ApplicationTableDesignService;
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
@RequestMapping("/api/v1/application-table-design")
@RequiredArgsConstructor
@Slf4j
public class ApplicationTableDesignController {

     private final ApplicationTableDesignService applicationTableDesignService;

    private final ApplicationTableDesignCriteriaService applicationTableDesignCriteriaService;

    private final ApplicationTableDesignRepository repository;

    private static final String ENTITY_NAME = "APP_TABLE_DESIGN";

    @GetMapping
    public ResponseEntity<Page<ApplicationTableDesignDTO>> getAllApplicationTableDesignsByCriteria(@ParameterObject ApplicationTableDesignCriteria criteria,
                                                                                           @ParameterObject  Pageable pageable) {
        Page<ApplicationTableDesignDTO> page = applicationTableDesignCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<ApplicationTableDesignDTO>> getAllApplicationTableDesigns(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all application table design , page: {}", pageable.getPageNumber());
        Page<ApplicationTableDesignDTO> page = applicationTableDesignService.getAllApplicationTableDesign(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<ApplicationTableDesignDTO> saveApplicationTableDesign(
            @Valid @RequestBody ApplicationTableDesignDTO dto) {
        log.info("Creating new application table design");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new app table design cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationTableDesignDTO saved = applicationTableDesignService.saveApplicationTableDesign(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationTableDesignDTO> getApplicationTableDesignById(@PathVariable Long id) {
        log.info("Fetching application table design with ID: {}", id);
        return applicationTableDesignService.getApplicationTableDesignById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Application table design not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationTableDesignDTO> updateApplicationTableDesign(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationTableDesignDTO dto) {
        log.info("Updating application table design with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        ApplicationTableDesignDTO updated = applicationTableDesignService.updateApplicationTableDesign(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplicationTableDesignById(@PathVariable Long id) {
        log.info("Deleting application table design with ID: {}", id);
        boolean deleted = applicationTableDesignService.deleteApplicationTableDesignById(id);
        if (deleted) {
            return ResponseEntity.ok("ApplicationTableDesign deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("ApplicationTableDesign deletion failed - ID: " + id);
        }
    }
}
