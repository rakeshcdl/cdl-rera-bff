package com.cdl.escrow.controller;

import com.cdl.escrow.criteria.CapitalPartnerPaymentPlanCriteria;
import com.cdl.escrow.criteriaservice.CapitalPartnerPaymentPlanCriteriaService;
import com.cdl.escrow.dto.CapitalPartnerPaymentPlanDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.CapitalPartnerPaymentPlanRepository;
import com.cdl.escrow.service.CapitalPartnerPaymentPlanService;
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
@RequestMapping("/api/v1/capital-partner-payment-plan")
@RequiredArgsConstructor
@Slf4j
public class CapitalPartnerPaymentPlanController {

    private final CapitalPartnerPaymentPlanService capitalPartnerPaymentPlanService;

    private final CapitalPartnerPaymentPlanRepository repository;

    private static final String ENTITY_NAME = "CAPITAL_PARTNER_PAYMENT_PLAN";

    private final CapitalPartnerPaymentPlanCriteriaService capitalPartnerPaymentPlanCriteriaService;


    @GetMapping
    public ResponseEntity<List<CapitalPartnerPaymentPlanDTO>> getAllCapitalPartnerPaymentPlanByCriteria(@ParameterObject CapitalPartnerPaymentPlanCriteria criteria,
                                                                                                          @ParameterObject Pageable pageable) {
        Page<CapitalPartnerPaymentPlanDTO> page = capitalPartnerPaymentPlanCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<CapitalPartnerPaymentPlanDTO>> getAllCapitalPartnerPaymentPlan(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all capital partner Payment Plan, page: {}", pageable.getPageNumber());
        Page<CapitalPartnerPaymentPlanDTO> page = capitalPartnerPaymentPlanService.getAllCapitalPartnerPaymentPlan(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<CapitalPartnerPaymentPlanDTO> saveCapitalPartnerPaymentPlan(
            @Valid @RequestBody CapitalPartnerPaymentPlanDTO dto) {
        log.info("Creating new capital partner Payment Plan");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new capital partner Payment Plan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CapitalPartnerPaymentPlanDTO saved = capitalPartnerPaymentPlanService.saveCapitalPartnerPaymentPlan(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CapitalPartnerPaymentPlanDTO> getCapitalPartnerPaymentPlanById(@PathVariable Long id) {
        log.info("Fetching capital partner Payment Plan with ID: {}", id);
        return capitalPartnerPaymentPlanService.getRCapitalPartnerPaymentPlanById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Capital partner not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<CapitalPartnerPaymentPlanDTO> updateCapitalPartnerPaymentPlan(
            @PathVariable Long id,
            @Valid @RequestBody CapitalPartnerPaymentPlanDTO dto) {
        log.info("Updating capital partner Payment Plan with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        CapitalPartnerPaymentPlanDTO updated = capitalPartnerPaymentPlanService.updateCapitalPartnerPaymentPlan(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCapitalPartnerPaymentPlanById(@PathVariable Long id) {
        log.info("Deleting capital partner Payment Plan with ID: {}", id);
        boolean deleted = capitalPartnerPaymentPlanService.deleteCapitalPartnerPaymentPlanById(id);
        if (deleted) {
            return ResponseEntity.ok("RealEstateAssest Payment Plan deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("RealEstateAssest Payment Plan  deletion failed - ID: " + id);
        }
    }

    @PostMapping("/save-all")
    public ResponseEntity<CapitalPartnerPaymentPlanDTO> saveAllCapitalPartnerPaymentPlan(
            @Valid @RequestBody List<CapitalPartnerPaymentPlanDTO> dto) {
        log.info("Creating all new capital partner Payment Plan");

        CapitalPartnerPaymentPlanDTO saved = capitalPartnerPaymentPlanService.saveAllCapitalPartnerPaymentPlan(dto);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteCapitalPartnerPaymentPlanServiceById(@PathVariable Long id) {
        log.info("Soft deleting CapitalPartnerPaymentPlan with ID: {}", id);

        boolean deleted = capitalPartnerPaymentPlanService.softCapitalPartnerPaymentPlanServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("CapitalPartnerPaymentPlan soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("CapitalPartnerPaymentPlan soft deletion failed - ID: " + id);
        }
    }
}
