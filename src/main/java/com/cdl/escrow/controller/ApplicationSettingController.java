package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.ApplicationSettingCriteria;
import com.cdl.escrow.criteriaservice.ApplicationSettingCriteriaService;
import com.cdl.escrow.dto.ApplicationSettingDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.ApplicationSettingRepository;
import com.cdl.escrow.service.ApplicationSettingService;
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
@RequestMapping("/api/v1/application-setting")
@RequiredArgsConstructor
@Slf4j
public class ApplicationSettingController {

    private final ApplicationSettingService applicationSettingService;

    private final ApplicationSettingCriteriaService applicationSettingCriteriaService;

    private final ApplicationSettingRepository repository;

    private static final String ENTITY_NAME = "APPLICATION_SETTING";

    @GetMapping
    public ResponseEntity<List<ApplicationSettingDTO>> getAllApplicationSettingsByCriteria(@ParameterObject ApplicationSettingCriteria criteria,
                                                                                         @ParameterObject  Pageable pageable) {
        Page<ApplicationSettingDTO> page = applicationSettingCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<ApplicationSettingDTO>> getAllApplicationSettings(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all application setting , page: {}", pageable.getPageNumber());
        Page<ApplicationSettingDTO> page = applicationSettingService.getAllApplicationSetting(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<ApplicationSettingDTO> saveApplicationSetting(
            @Valid @RequestBody ApplicationSettingDTO dto) {
        log.info("Creating new application setting");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new application setting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationSettingDTO saved = applicationSettingService.saveApplicationSetting(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationSettingDTO> getApplicationSettingById(@PathVariable Long id) {
        log.info("Fetching application setting with ID: {}", id);
        return applicationSettingService.getApplicationSettingById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Application setting not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationSettingDTO> updateApplicationSetting(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationSettingDTO dto) {
        log.info("Updating application setting with ID: {}", id);

        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        ApplicationSettingDTO updated = applicationSettingService.updateApplicationSetting(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApplicationSettingById(@PathVariable Long id) {
        log.info("Deleting application setting with ID: {}", id);
        boolean deleted = applicationSettingService.deleteApplicationSettingById(id);
        if (deleted) {
            return ResponseEntity.ok("ApplicationSetting deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("ApplicationSetting deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteApplicationSettingById(@PathVariable Long id) {
        log.info("Soft deleting ApplicationSetting with ID: {}", id);

        boolean deleted = applicationSettingService.softApplicationSettingById(id);
        if (deleted) {
            return ResponseEntity.ok("ApplicationSetting soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("ApplicationSetting soft deletion failed - ID: " + id);
        }
    }
}
