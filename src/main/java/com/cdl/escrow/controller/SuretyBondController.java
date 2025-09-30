package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.SuretyBondCriteria;
import com.cdl.escrow.criteriaservice.SuretyBondCriteriaService;
import com.cdl.escrow.dto.PendingFundIngressDTO;
import com.cdl.escrow.dto.SuretyBondDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.SuretyBondRepository;
import com.cdl.escrow.service.SuretyBondService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/surety-bond")
@RequiredArgsConstructor
@Slf4j
public class SuretyBondController {

    private final SuretyBondService suretyBondService;

    private final SuretyBondCriteriaService suretyBondCriteriaService;

    private final SuretyBondRepository repository;

    private static final String ENTITY_NAME = "SURETY_BOND";

    @GetMapping
    public ResponseEntity<Page<SuretyBondDTO>> getAllSuretyBondByCriteria(@ParameterObject SuretyBondCriteria criteria,
                                                                                               @ParameterObject  Pageable pageable) {
        Page<SuretyBondDTO> page = suretyBondCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<SuretyBondDTO>> getAllSuretyBond(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all surety bond, page: {}", pageable.getPageNumber());
        Page<SuretyBondDTO> page = suretyBondService.getAllSuretyBond(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<SuretyBondDTO> saveSuretyBond(
            @Valid @RequestBody SuretyBondDTO dto) {
        log.info("Creating new surety bond");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new surety bond cannot already have an ID",ENTITY_NAME , "idexists");
        }
        SuretyBondDTO saved = suretyBondService.saveSuretyBond(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuretyBondDTO> getSuretyBondById(@PathVariable Long id) {
        log.info("Fetching surety bond with ID: {}", id);
        return suretyBondService.getSuretyBondById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Surety bond not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuretyBondDTO> updateSuretyBond(
            @PathVariable Long id,
            @Valid @RequestBody SuretyBondDTO dto) {
        log.info("Updating surety bond with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        SuretyBondDTO updated = suretyBondService.updateSuretyBond(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSuretyBondById(@PathVariable Long id) {
        log.info("Deleting surety bond with ID: {}", id);
        boolean deleted = suretyBondService.deleteSuretyBondById(id);
        if (deleted) {
            return ResponseEntity.ok("SuretyBond deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("SuretyBond deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteSuretyBondServiceById(@PathVariable Long id) {
        log.info("Soft deleting SuretyBond with ID: {}", id);

        boolean deleted = suretyBondService.softSuretyBondServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("SuretyBond soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("SuretyBond soft deletion failed - ID: " + id);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<SuretyBondDTO> uploadSuretyBondTemplate(
            @RequestParam("file") MultipartFile file) {
        log.info("Bulk upload build surety bond");

       /* if (dto.getId() != null) {
            throw new BadRequestAlertException("A new build partner cannot already have an ID", ENTITY_NAME, "idexists");
        }*/
        // BuildPartnerDTO saved = buildPartnerService.saveBuildPartner(dto);
        // return ResponseEntity.ok(saved);
        return null;
    }

}
