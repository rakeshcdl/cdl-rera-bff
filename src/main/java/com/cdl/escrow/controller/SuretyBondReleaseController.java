package com.cdl.escrow.controller;

import com.cdl.escrow.criteria.SuretyBondReleaseCriteria;
import com.cdl.escrow.criteriaservice.SuretyBondReleaseCriteriaService;
import com.cdl.escrow.dto.SuretyBondReleaseDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.SuretyBondReleaseRepository;
import com.cdl.escrow.service.SuretyBondReleaseService;
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
@RequestMapping("/api/v1/surety-bond-release")
@RequiredArgsConstructor
@Slf4j
public class SuretyBondReleaseController {

    private final SuretyBondReleaseService suretyBondReleaseService;

    private final SuretyBondReleaseCriteriaService suretyBondReleaseCriteriaService;

    private final SuretyBondReleaseRepository repository;

    private static final String ENTITY_NAME = "SURETY_BOND_RELEASE";

    @GetMapping
    public ResponseEntity<Page<SuretyBondReleaseDTO>> getAllSuretyBondReleaseyByCriteria(@ParameterObject SuretyBondReleaseCriteria criteria,
                                                                                           @ParameterObject  Pageable pageable) {
        Page<SuretyBondReleaseDTO> page = suretyBondReleaseCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }


    @GetMapping("/find-all")
    public ResponseEntity<Page<SuretyBondReleaseDTO>> getAllSuretyBondRelease(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all surety bond release, page: {}", pageable.getPageNumber());
        Page<SuretyBondReleaseDTO> page = suretyBondReleaseService.getAllSuretyBondRelease(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<SuretyBondReleaseDTO> saveSuretyBondRelease(
            @Valid @RequestBody SuretyBondReleaseDTO dto) {
        log.info("Creating new surety bond release");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new surety bond release cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SuretyBondReleaseDTO saved = suretyBondReleaseService.saveSuretyBondRelease(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuretyBondReleaseDTO> getSuretyBondReleaseById(@PathVariable Long id) {
        log.info("Fetching surety bond release with ID: {}", id);
        return suretyBondReleaseService.getSuretyBondReleaseById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Surety bond not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuretyBondReleaseDTO> updateSuretyBondRelease(
            @PathVariable Long id,
            @Valid @RequestBody SuretyBondReleaseDTO dto) {
        log.info("Updating surety bond release with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        SuretyBondReleaseDTO updated = suretyBondReleaseService.updateSuretyBondRelease(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSuretyBondReleaseById(@PathVariable Long id) {
        log.info("Deleting surety bond release with ID: {}", id);
        boolean deleted = suretyBondReleaseService.deleteSuretyBondReleaseById(id);
        if (deleted) {
            return ResponseEntity.ok("SuretyBondRelease deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("SuretyBondRelease deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteSuretyBondReleaseServiceById(@PathVariable Long id) {
        log.info("Soft deleting SuretyBondRelease with ID: {}", id);

        boolean deleted = suretyBondReleaseService.softSuretyBondReleaseServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("SuretyBondRelease soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("SuretyBondRelease soft deletion failed - ID: " + id);
        }
    }
}
