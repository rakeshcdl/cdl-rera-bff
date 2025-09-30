package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.ApplicationFormDesignCriteria;
import com.cdl.escrow.criteriaservice.ApplicationFormDesignCriteriaService;
import com.cdl.escrow.dto.ApplicationFormDesignDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.ApplicationFormDesignRepository;
import com.cdl.escrow.service.ApplicationFormDesignService;
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
@RequestMapping("/api/v1/application-form-design")
@RequiredArgsConstructor
@Slf4j
public class ApplicationFormDesignController {

    private final ApplicationFormDesignService applicationFormDesignService;

    private final ApplicationFormDesignCriteriaService applicationFormDesignCriteriaService;

    private final ApplicationFormDesignRepository repository;

    private static final String ENTITY_NAME = "APPLICATION_FORM_DESIGN";

    @GetMapping
    public ResponseEntity<Page<ApplicationFormDesignDTO>> getAllApplicationFormDesignsByCriteria(@ParameterObject ApplicationFormDesignCriteria criteria,
                                                                                                       @ParameterObject  Pageable pageable) {
        Page<ApplicationFormDesignDTO> page = applicationFormDesignCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }
    @GetMapping("/find-all")
    public ResponseEntity<Page<ApplicationFormDesignDTO>> getAllApplicationFormDesigns(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all application form design , page: {}", pageable.getPageNumber());
        Page<ApplicationFormDesignDTO> page = applicationFormDesignService.getAllApplicationFormDesign(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<ApplicationFormDesignDTO> saveApplicationFormDesign(
            @Valid @RequestBody ApplicationFormDesignDTO dto) {
        log.info("Creating new application form design");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new application form design cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationFormDesignDTO saved = applicationFormDesignService.saveApplicationFormDesign(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationFormDesignDTO> getApplicationFormDesignById(@PathVariable Long id) {
        log.info("Fetching application form design with ID: {}", id);
        return applicationFormDesignService.getApplicationFormDesignById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Application form design not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationFormDesignDTO> updateApplicationFormDesign(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationFormDesignDTO dto) {
        log.info("Updating application form design with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        ApplicationFormDesignDTO updated = applicationFormDesignService.updateApplicationFormDesign(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplicationFormDesignById(@PathVariable Long id) {
        log.info("Deleting application form design with ID: {}", id);
        boolean deleted = applicationFormDesignService.deleteApplicationFormDesignById(id);
        if (deleted) {
            return ResponseEntity.ok("ApplicationFormDesign deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("ApplicationFormDesign deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteApplicationFormDesignById(@PathVariable Long id) {
        log.info("Soft deleting ApplicationFormDesign with ID: {}", id);

        boolean deleted = applicationFormDesignService.softDeleteApplicationFormDesignById(id);
        if (deleted) {
            return ResponseEntity.ok("ApplicationFormDesign soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("ApplicationFormDesign soft deletion failed - ID: " + id);
        }
    }
}
