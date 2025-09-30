package com.cdl.escrow.controller;

import com.cdl.escrow.criteria.FeatureFlagCriteria;
import com.cdl.escrow.criteriaservice.FeatureFlagCriteriaService;
import com.cdl.escrow.dto.FeatureFlagDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.FeatureFlagRepository;
import com.cdl.escrow.service.FeatureFlagService;
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
@RequestMapping("/api/v1/feature-flag")
@RequiredArgsConstructor
@Slf4j
public class FeatureFlagController {
    private final FeatureFlagService featureFlagService;

    private final FeatureFlagCriteriaService featureFlagCriteriaService;

    private final FeatureFlagRepository repository;

    private static final String ENTITY_NAME = "FEATURE_FLAG";

    @GetMapping
    public ResponseEntity<Page<FeatureFlagDTO>> getAllFeatureFlagByCriteria(@ParameterObject FeatureFlagCriteria criteria,
                                                                                           @ParameterObject  Pageable pageable) {
        Page<FeatureFlagDTO> page = featureFlagCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }


    @GetMapping("/find-all")
    public ResponseEntity<Page<FeatureFlagDTO>> getAllFeatureFlag(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all feature flag, page: {}", pageable.getPageNumber());
        Page<FeatureFlagDTO> page = featureFlagService.getAllFeatureFlag(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<FeatureFlagDTO> saveFeatureFlag(
            @Valid @RequestBody FeatureFlagDTO dto) {
        log.info("Creating new feature flag");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new feature flag info cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeatureFlagDTO saved = featureFlagService.saveFeatureFlag(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeatureFlagDTO> getFeatureFlagById(@PathVariable Long id) {
        log.info("Fetching feature flag with ID: {}", id);
        return featureFlagService.getFeatureFlagById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Feature flag not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<FeatureFlagDTO> updateFeatureFlag(
            @PathVariable Long id,
            @Valid @RequestBody FeatureFlagDTO dto) {
        log.info("Updating feature flag with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        FeatureFlagDTO updated = featureFlagService.updateFeatureFlag(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFeatureFlagById(@PathVariable Long id) {
        log.info("Deleting feature flag with ID: {}", id);
        boolean deleted = featureFlagService.deleteFeatureFlagById(id);
        if (deleted) {
            return ResponseEntity.ok("FeatureFlag deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("FeatureFlag deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteFeatureFlagServiceById(@PathVariable Long id) {
        log.info("Soft deleting FeatureFlag with ID: {}", id);

        boolean deleted = featureFlagService.softFeatureFlagServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("FeatureFlag soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("FeatureFlag soft deletion failed - ID: " + id);
        }
    }
}
