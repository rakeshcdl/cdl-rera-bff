package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.RealEstateAssestPaymentPlanCriteria;
import com.cdl.escrow.criteriaservice.RealEstateAssestPaymentPlanCriteriaService;
import com.cdl.escrow.dto.RealEstateAssestPaymentPlanDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.RealEstateAssestPaymentPlanRepository;
import com.cdl.escrow.service.RealEstateAssestPaymentPlanService;
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
@RequestMapping("/api/v1/real-estate-assest-payment-plan")
@RequiredArgsConstructor
@Slf4j
public class RealEstateAssestPaymentPlanController {

    private final RealEstateAssestPaymentPlanService realEstateAssestPaymentPlanService;

    private final RealEstateAssestPaymentPlanRepository repository;

    private static final String ENTITY_NAME = "REAL_ESTATE_ASSEST_PAYMENT_PLAN";

    private final RealEstateAssestPaymentPlanCriteriaService realEstateAssestPaymentPlanCriteriaService;


    @GetMapping
    public ResponseEntity<Page<RealEstateAssestPaymentPlanDTO>> getAllRealEstateAssestPaymentPlanByCriteria(@ParameterObject RealEstateAssestPaymentPlanCriteria criteria,
                                                                                      @ParameterObject  Pageable pageable) {
        Page<RealEstateAssestPaymentPlanDTO> page = realEstateAssestPaymentPlanCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }


    @GetMapping("/find-all")
    public ResponseEntity<Page<RealEstateAssestPaymentPlanDTO>> getAllRealEstateAssestPaymentPlan(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all real estate assest Payment Plan, page: {}", pageable.getPageNumber());
        Page<RealEstateAssestPaymentPlanDTO> page = realEstateAssestPaymentPlanService.getAllRealEstateAssestPaymentPlan(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<RealEstateAssestPaymentPlanDTO> saveRealEstateAssestPaymentPlan(
            @Valid @RequestBody RealEstateAssestPaymentPlanDTO dto) {
        log.info("Creating new real estate assest Payment Plan");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new real estate assests Payment Plan cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RealEstateAssestPaymentPlanDTO saved = realEstateAssestPaymentPlanService.saveRealEstateAssestPaymentPlan(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RealEstateAssestPaymentPlanDTO> getRealEstateAssestPaymentPlanById(@PathVariable Long id) {
        log.info("Fetching real estate assest Payment Plan with ID: {}", id);
        return realEstateAssestPaymentPlanService.getRealEstateAssestPaymentPlanById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Real estate assest not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<RealEstateAssestPaymentPlanDTO> updateRealEstateAssestPaymentPlan(
            @PathVariable Long id,
            @Valid @RequestBody RealEstateAssestPaymentPlanDTO dto) {
        log.info("Updating real estate assest Payment Plan with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RealEstateAssestPaymentPlanDTO updated = realEstateAssestPaymentPlanService.updateRealEstateAssestPaymentPlan(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRealEstateAssestPaymentPlanById(@PathVariable Long id) {
        log.info("Deleting real estate assest Payment Plan with ID: {}", id);
        boolean deleted = realEstateAssestPaymentPlanService.deleteRealEstateAssestPaymentPlanById(id);
        if (deleted) {
            return ResponseEntity.ok("RealEstateAssest Payment Plan deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("RealEstateAssest Payment Plan  deletion failed - ID: " + id);
        }
    }

    @PostMapping("/save-all")
    public ResponseEntity<RealEstateAssestPaymentPlanDTO> saveAllRealEstateAssestPaymentPlan(
            @Valid @RequestBody List<RealEstateAssestPaymentPlanDTO> dto) {
        log.info("Creating all new real estate assest Payment Plan");

        RealEstateAssestPaymentPlanDTO saved = realEstateAssestPaymentPlanService.saveAllRealEstateAssestPaymentPlan(dto);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteRealEstateAssestPaymentPlanServiceById(@PathVariable Long id) {
        log.info("Soft deleting RealEstateAssestPaymentPlan with ID: {}", id);

        boolean deleted = realEstateAssestPaymentPlanService.softRealEstateAssestPaymentPlanServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("RealEstateAssestPaymentPlan soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("RealEstateAssestPaymentPlan soft deletion failed - ID: " + id);
        }
    }
}
