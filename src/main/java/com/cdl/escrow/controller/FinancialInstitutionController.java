package com.cdl.escrow.controller;

import com.cdl.escrow.criteria.FinancialInstitutionCriteria;
import com.cdl.escrow.criteriaservice.FinancialInstitutionCriteriaService;
import com.cdl.escrow.dto.FinancialInstitutionDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.FinancialInstitutionRepository;
import com.cdl.escrow.service.FinancialInstitutionService;
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
@RequestMapping("/api/v1/financial-institution")
@RequiredArgsConstructor
@Slf4j
public class FinancialInstitutionController {

    private final FinancialInstitutionService financialInstitutionService;

    private final FinancialInstitutionCriteriaService financialInstitutionCriteriaService;

    private final FinancialInstitutionRepository repository;

    private static final String ENTITY_NAME = "FINANCIAL_INSTITUTION";

    @GetMapping
    public ResponseEntity<Page<FinancialInstitutionDTO>> getAllFinancialInstitutionByCriteria(@ParameterObject FinancialInstitutionCriteria criteria,
                                                                            @ParameterObject  Pageable pageable) {
        Page<FinancialInstitutionDTO> page = financialInstitutionCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }


    @GetMapping("/find-all")
    public ResponseEntity<Page<FinancialInstitutionDTO>> getAllFinancialInstitution(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all financial institution, page: {}", pageable.getPageNumber());
        Page<FinancialInstitutionDTO> page = financialInstitutionService.getAllFinancialInstitution(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<FinancialInstitutionDTO> saveFinancialInstitution(
            @Valid @RequestBody FinancialInstitutionDTO dto) {
        log.info("Creating new financial institution");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new financial institution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FinancialInstitutionDTO saved = financialInstitutionService.saveFinancialInstitution(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinancialInstitutionDTO> getFinancialInstitutionById(@PathVariable Long id) {
        log.info("Fetching financial institution with ID: {}", id);
        return financialInstitutionService.getFinancialInstitutionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Financial institution not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinancialInstitutionDTO> updateFinancialInstitution(
            @PathVariable Long id,
            @Valid @RequestBody FinancialInstitutionDTO dto) {
        log.info("Updating financial institution with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        FinancialInstitutionDTO updated = financialInstitutionService.updateFinancialInstitution(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFinancialInstitutionById(@PathVariable Long id) {
        log.info("Deleting financial institution with ID: {}", id);
        boolean deleted = financialInstitutionService.deleteFinancialInstitutionById(id);
        if (deleted) {
            return ResponseEntity.ok("FinancialInstitution deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("FinancialInstitution deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteFinancialInstitutionServiceById(@PathVariable Long id) {
        log.info("Soft deleting FinancialInstitution with ID: {}", id);

        boolean deleted = financialInstitutionService.softFinancialInstitutionServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("FinancialInstitution soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("FinancialInstitution soft deletion failed - ID: " + id);
        }
    }

}
