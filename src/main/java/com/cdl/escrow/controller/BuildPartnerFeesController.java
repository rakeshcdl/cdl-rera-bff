package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.BuildPartnerFeesCriteria;
import com.cdl.escrow.criteriaservice.BuildPartnerFeesCriteriaService;
import com.cdl.escrow.dto.BuildPartnerFeesDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.BuildPartnerFeesRepository;
import com.cdl.escrow.service.BuildPartnerFeesService;
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
@RequestMapping("/api/v1/build-partner-fees")
@RequiredArgsConstructor
@Slf4j
public class BuildPartnerFeesController {

   private final BuildPartnerFeesService buildPartnerFeesService;

   private final BuildPartnerFeesCriteriaService buildPartnerFeesCriteriaService;

    private final BuildPartnerFeesRepository repository;

    private static final String ENTITY_NAME = "BUILD_PARTNER_FEES";

    @GetMapping
    public ResponseEntity<Page<BuildPartnerFeesDTO>> getAllBuildPartnerFeesByCriteria(@ParameterObject BuildPartnerFeesCriteria criteria,
                                                                               @ParameterObject  Pageable pageable) {
        Page<BuildPartnerFeesDTO> page = buildPartnerFeesCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<BuildPartnerFeesDTO>> getAllBuildPartnerFees(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all build partner fees, page: {}", pageable.getPageNumber());
        Page<BuildPartnerFeesDTO> page = buildPartnerFeesService.getAllBuildPartnerFees(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<BuildPartnerFeesDTO> saveBuildPartnerFees(
            @Valid @RequestBody BuildPartnerFeesDTO dto) {
        log.info("Creating new build partner fees");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new build partner fees cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuildPartnerFeesDTO saved = buildPartnerFeesService.saveBuildPartnerFees(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildPartnerFeesDTO> getBuildPartnerFeesById(@PathVariable Long id) {
        log.info("Fetching build partner fees with ID: {}", id);
        return buildPartnerFeesService.getBuildPartnerFeesById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Build partner fees not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuildPartnerFeesDTO> updateBuildPartnerFees(
            @PathVariable Long id,
            @Valid @RequestBody BuildPartnerFeesDTO dto) {
        log.info("Updating build partner fees with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        BuildPartnerFeesDTO updated = buildPartnerFeesService.updateBuildPartnerFees(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBuildPartnerFeesById(@PathVariable Long id) {
        log.info("Deleting build partner fees with ID: {}", id);
        boolean deleted = buildPartnerFeesService.deleteBuildPartnerFeesById(id);
        if (deleted) {
            return ResponseEntity.ok("BuildPartnerFees deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("BuildPartnerFees deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteBuildPartnerFeesServiceById(@PathVariable Long id) {
        log.info("Soft deleting BuildPartnerFees with ID: {}", id);

        boolean deleted = buildPartnerFeesService.softBuildPartnerFeesServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("BuildPartnerFees soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("BuildPartnerFees soft deletion failed - ID: " + id);
        }
    }
}
