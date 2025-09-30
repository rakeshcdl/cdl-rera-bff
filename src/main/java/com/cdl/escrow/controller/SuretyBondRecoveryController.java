package com.cdl.escrow.controller;

import com.cdl.escrow.criteria.SuretyBondRecoveryCriteria;
import com.cdl.escrow.criteriaservice.SuretyBondRecoveryCriteriaService;
import com.cdl.escrow.dto.SuretyBondRecoveryDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.SuretyBondRecoveryRepository;
import com.cdl.escrow.service.SuretyBondRecoveryService;
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
@RequestMapping("/api/v1/surety-bond-recovery")
@RequiredArgsConstructor
@Slf4j
public class SuretyBondRecoveryController {

    private final SuretyBondRecoveryService suretyBondRecoveryService;

    private final SuretyBondRecoveryCriteriaService suretyBondRecoveryCriteriaService;

    private final SuretyBondRecoveryRepository repository;

    private static final String ENTITY_NAME = "SURETY_BOND_RECOVERY";

    @GetMapping
    public ResponseEntity<Page<SuretyBondRecoveryDTO>> getAllSuretyBondRecoveryByCriteria(@ParameterObject SuretyBondRecoveryCriteria criteria,
                                                                          @ParameterObject  Pageable pageable) {
        Page<SuretyBondRecoveryDTO> page = suretyBondRecoveryCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }


    @GetMapping("/find-all")
    public ResponseEntity<Page<SuretyBondRecoveryDTO>> getAllSuretyBondRecovery(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all surety bond recovery, page: {}", pageable.getPageNumber());
        Page<SuretyBondRecoveryDTO> page = suretyBondRecoveryService.getAllSuretyBondRecovery(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<SuretyBondRecoveryDTO> saveSuretyBondRecovery(
            @Valid @RequestBody SuretyBondRecoveryDTO dto) {
        log.info("Creating new surety bond recovery");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new surety bond recovery cannot already have an ID",ENTITY_NAME, "idexists");
        }
        SuretyBondRecoveryDTO saved = suretyBondRecoveryService.saveSuretyBondRecovery(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuretyBondRecoveryDTO> getSuretyBondRecoveryById(@PathVariable Long id) {
        log.info("Fetching surety bond recovery with ID: {}", id);
        return suretyBondRecoveryService.getSuretyBondRecoveryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Surety bond not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuretyBondRecoveryDTO> updateSuretyBondRecovery(
            @PathVariable Long id,
            @Valid @RequestBody SuretyBondRecoveryDTO dto) {
        log.info("Updating surety bond recovery with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        SuretyBondRecoveryDTO updated = suretyBondRecoveryService.updateSuretyBondRecovery(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSuretyBondRecoveryById(@PathVariable Long id) {
        log.info("Deleting surety bond recovery with ID: {}", id);
        boolean deleted = suretyBondRecoveryService.deleteSuretyBondRecoveryById(id);
        if (deleted) {
            return ResponseEntity.ok("SuretyBondRecovery deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("SuretyBondRecovery deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteSuretyBondRecoveryServiceById(@PathVariable Long id) {
        log.info("Soft deleting SuretyBondRecovery with ID: {}", id);

        boolean deleted = suretyBondRecoveryService.softSuretyBondRecoveryServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("SuretyBondRecovery soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("SuretyBondRecovery soft deletion failed - ID: " + id);
        }
    }
}
