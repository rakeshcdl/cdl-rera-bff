package com.cdl.escrow.controller;

import com.cdl.escrow.criteria.FundEgressCriteria;
import com.cdl.escrow.criteriaservice.FundEgressCriteriaService;
import com.cdl.escrow.dto.FundEgressDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.FundEgressRepository;
import com.cdl.escrow.service.FundEgressService;
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
@RequestMapping("/api/v1/fund-egress")
@RequiredArgsConstructor
@Slf4j
public class FundEgressController {

    private final FundEgressService fundEgressService;

    private final FundEgressCriteriaService fundEgressCriteriaService;

    private final FundEgressRepository repository;

    private static final String ENTITY_NAME = "FUND_EGRESS";

    @GetMapping
    public ResponseEntity<Page<FundEgressDTO>> getAllFundEgressByCriteria(@ParameterObject FundEgressCriteria criteria,
                                                                                              @ParameterObject  Pageable pageable) {
        Page<FundEgressDTO> page = fundEgressCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<FundEgressDTO>> getAllFundEgress(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all fund egress, page: {}", pageable.getPageNumber());
        Page<FundEgressDTO> page = fundEgressService.getAllFundEgress(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<FundEgressDTO> saveFundEgress(
            @Valid @RequestBody FundEgressDTO dto) {
        log.info("Creating new fund egress");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new fund egress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FundEgressDTO saved = fundEgressService.saveFundEgress(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FundEgressDTO> getFundEgressById(@PathVariable Long id) {
        log.info("Fetching fund egress with ID: {}", id);
        return fundEgressService.getFundEgressById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Fund egress not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<FundEgressDTO> updateFundEgress(
            @PathVariable Long id,
            @Valid @RequestBody FundEgressDTO dto) {
        log.info("Updating fund egress with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        FundEgressDTO updated = fundEgressService.updateFundEgress(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFundEgressById(@PathVariable Long id) {
        log.info("Deleting fund egress with ID: {}", id);
        boolean deleted = fundEgressService.deleteFundEgressById(id);
        if (deleted) {
            return ResponseEntity.ok("FundEgress deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("FundEgress deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteFundEgressServiceById(@PathVariable Long id) {
        log.info("Soft deleting FundEgress with ID: {}", id);

        boolean deleted = fundEgressService.softFundEgressServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("FundEgress soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("FundEgress soft deletion failed - ID: " + id);
        }
    }

}
