package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.RealEstateAssestFinancialSummaryCriteria;
import com.cdl.escrow.criteriaservice.RealEstateAssestFinancialSummaryCriteriaService;
import com.cdl.escrow.dto.RealEstateAssestFinancialSummaryDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.RealEstateAssestFinancialSummaryRepository;
import com.cdl.escrow.service.RealEstateAssestFinancialSummaryService;
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
@RequestMapping("/api/v1/real-estate-asset-financial-summary")
@RequiredArgsConstructor
@Slf4j
public class RealEstateAssestFinancialSummaryController {

     private final RealEstateAssestFinancialSummaryService realEstateAssestFinancialSummaryService;

     private final RealEstateAssestFinancialSummaryCriteriaService realEstateAssestFinancialSummaryCriteriaService;

    private final RealEstateAssestFinancialSummaryRepository repository;

    private static final String ENTITY_NAME = "REAL_ESTATE_FINANCIAL_SUMMARY";

    @GetMapping
    public ResponseEntity<Page<RealEstateAssestFinancialSummaryDTO>> getAllRealEstateAssestFinancialSummaryByCriteria(@ParameterObject RealEstateAssestFinancialSummaryCriteria criteria,
                                                                                                          @ParameterObject  Pageable pageable) {
        Page<RealEstateAssestFinancialSummaryDTO> page = realEstateAssestFinancialSummaryCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<RealEstateAssestFinancialSummaryDTO>> getAllRealEstateAssestFinancialSummary(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all real estate assest financial summary, page: {}", pageable.getPageNumber());
        Page<RealEstateAssestFinancialSummaryDTO> page = realEstateAssestFinancialSummaryService.getAllRealEstateAssestFinancialSummary(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<RealEstateAssestFinancialSummaryDTO> saveRealEstateAssestFinancialSummary(
            @Valid @RequestBody RealEstateAssestFinancialSummaryDTO dto) {
        log.info("Creating new real estate assest financial summary");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new real estate financial summary cannot already have an ID", ENTITY_NAME , "idexists");
        }
        RealEstateAssestFinancialSummaryDTO saved = realEstateAssestFinancialSummaryService.saveRealEstateAssestFinancialSummary(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RealEstateAssestFinancialSummaryDTO> getRealEstateAssestFinancialSummaryById(@PathVariable Long id) {
        log.info("Fetching real estate assest financial summary with ID: {}", id);
        return realEstateAssestFinancialSummaryService.getRealEstateAssestFinancialSummaryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Real estate assest financial summary not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<RealEstateAssestFinancialSummaryDTO> updateRealEstateAssestFinancialSummary(
            @PathVariable Long id,
            @Valid @RequestBody RealEstateAssestFinancialSummaryDTO dto) {
        log.info("Updating real estate assest financial summary with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RealEstateAssestFinancialSummaryDTO updated = realEstateAssestFinancialSummaryService.updateRealEstateAssestFinancialSummary(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRealEstateAssestFinancialSummaryById(@PathVariable Long id) {
        log.info("Deleting real estate assest financial summary with ID: {}", id);
        boolean deleted = realEstateAssestFinancialSummaryService.deleteRealEstateAssestFinancialSummaryById(id);
        if (deleted) {
            return ResponseEntity.ok("RealEstateAssestFinancialSummary deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("RealEstateAssestFinancialSummary deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteRealEstateAssestFinancialSummaryServiceById(@PathVariable Long id) {
        log.info("Soft deleting RealEstateAssestFinancialSummary with ID: {}", id);

        boolean deleted = realEstateAssestFinancialSummaryService.softRealEstateAssestFinancialSummaryServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("RealEstateAssestFinancialSummary soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("RealEstateAssestFinancialSummary soft deletion failed - ID: " + id);
        }
    }
}
