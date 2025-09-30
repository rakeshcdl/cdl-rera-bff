package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.CapitalPartnerUnitPurchaseCriteria;
import com.cdl.escrow.criteriaservice.CapitalPartnerUnitPurchaseCriteriaService;
import com.cdl.escrow.dto.CapitalPartnerUnitPurchaseDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.CapitalPartnerUnitPurchaseRepository;
import com.cdl.escrow.service.CapitalPartnerUnitPurchaseService;
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
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/capital-partner-unit-purchase")
@RequiredArgsConstructor
@Slf4j
public class CapitalPartnerUnitPurchaseController {

    private final CapitalPartnerUnitPurchaseService capitalPartnerUnitPurchaseService;

    private final CapitalPartnerUnitPurchaseCriteriaService capitalPartnerUnitPurchaseCriteriaService;

    private final CapitalPartnerUnitPurchaseRepository repository;

    private static final String ENTITY_NAME = "CAPITAL_PARTNER_UNIT_PURCHASE";

    @GetMapping
    public ResponseEntity<List<CapitalPartnerUnitPurchaseDTO>> getAllCapitalPartnerUnitPurchaseByCriteria(@ParameterObject CapitalPartnerUnitPurchaseCriteria criteria,
                                                                                           @ParameterObject  Pageable pageable) {
        Page<CapitalPartnerUnitPurchaseDTO> page = capitalPartnerUnitPurchaseCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<CapitalPartnerUnitPurchaseDTO>> getAllCapitalPartnerUnitPurchase(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all capital partner unit purchase, page: {}", pageable.getPageNumber());
        Page<CapitalPartnerUnitPurchaseDTO> page = capitalPartnerUnitPurchaseService.getAllCapitalPartnerUnitPurchase(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<CapitalPartnerUnitPurchaseDTO> saveCapitalPartnerUnitPurchase(
            @Valid @RequestBody CapitalPartnerUnitPurchaseDTO dto) {
        log.info("Creating new capital partner unit purchase");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new capital partner unit purchase cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CapitalPartnerUnitPurchaseDTO saved = capitalPartnerUnitPurchaseService.saveCapitalPartnerUnitPurchase(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CapitalPartnerUnitPurchaseDTO> getCapitalPartnerUnitPurchaseById(@PathVariable Long id) {
        log.info("Fetching capital partner unit purchase with ID: {}", id);
        return capitalPartnerUnitPurchaseService.getCapitalPartnerUnitPurchaseById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Capital partner unit purchase not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<CapitalPartnerUnitPurchaseDTO> updateCapitalPartnerUnitPurchase(
            @PathVariable Long id,
            @Valid @RequestBody CapitalPartnerUnitPurchaseDTO dto) {
        log.info("Updating capital partner unit purchase with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        CapitalPartnerUnitPurchaseDTO updated = capitalPartnerUnitPurchaseService.updateCapitalPartnerUnitPurchase(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCapitalPartnerUnitPurchaseById(@PathVariable Long id) {
        log.info("Deleting capital partner purchase unit with ID: {}", id);
        boolean deleted = capitalPartnerUnitPurchaseService.deleteCapitalPartnerUnitPurchaseById(id);
        if (deleted) {
            return ResponseEntity.ok("CapitalPartnerUnitPurchase deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("CapitalPartnerUnitPurchase deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteCapitalPartnerUnitPurchaseServiceById(@PathVariable Long id) {
        log.info("Soft deleting CapitalPartnerUnitPurchase with ID: {}", id);

        boolean deleted = capitalPartnerUnitPurchaseService.softCapitalPartnerUnitPurchaseServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("CapitalPartnerUnitPurchase soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("CapitalPartnerUnitPurchase soft deletion failed - ID: " + id);
        }
    }

}
