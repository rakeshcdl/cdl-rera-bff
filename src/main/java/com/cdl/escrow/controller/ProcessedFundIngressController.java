package com.cdl.escrow.controller;

import com.cdl.escrow.criteria.ProcessedFundIngressCriteria;
import com.cdl.escrow.criteriaservice.ProcessedFundIngressCriteriaService;
import com.cdl.escrow.dto.ProcessedFundIngressDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.ProcessedFundIngressRepository;
import com.cdl.escrow.service.ProcessedFundIngressService;
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
@RequestMapping("/api/v1/processed-fund-ingress")
@RequiredArgsConstructor
@Slf4j
public class ProcessedFundIngressController {

    private final ProcessedFundIngressService processedFundIngressService;

    private final ProcessedFundIngressCriteriaService criteriaService;

    private final ProcessedFundIngressRepository repository;

    private static final String ENTITY_NAME = "PROCESSED_FUND_INGRESS";

    @GetMapping
    public ResponseEntity<Page<ProcessedFundIngressDTO>> getAllProcessedFundIngressByCriteria(@ParameterObject ProcessedFundIngressCriteria criteria,
                                                                                                @ParameterObject  Pageable pageable) {
        Page<ProcessedFundIngressDTO> page = criteriaService.findByCriteria(criteria,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<ProcessedFundIngressDTO>> getAllProcessedFundIngress(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all Processed fund ingress, page: {}", pageable.getPageNumber());
        Page<ProcessedFundIngressDTO> page = processedFundIngressService.getAllProcessedFundIngress(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<ProcessedFundIngressDTO> saveProcessedFundIngress(
            @Valid @RequestBody ProcessedFundIngressDTO dto) {
        log.info("Creating new Processed fund ingress");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new processed fund ingress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessedFundIngressDTO saved = processedFundIngressService.saveProcessedFundIngress(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcessedFundIngressDTO> getProcessedFundIngressById(@PathVariable Long id) {
        log.info("Fetching Processed fund ingress with ID: {}", id);
        return processedFundIngressService.getProcessedFundIngressById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Processed Fund ingress not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcessedFundIngressDTO> updateProcessedFundIngress(
            @PathVariable Long id,
            @Valid @RequestBody ProcessedFundIngressDTO dto) {
        log.info("Updating Processed fund ingress with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        ProcessedFundIngressDTO updated = processedFundIngressService.updateProcessedFundIngress(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProcessedFundIngressById(@PathVariable Long id) {
        log.info("Deleting fund ingress with ID: {}", id);
        boolean deleted = processedFundIngressService.deleteProcessedFundIngressById(id);
        if (deleted) {
            return ResponseEntity.ok("ProcessedFundIngress deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("ProcessedFundIngress deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteProcessedFundIngressServiceById(@PathVariable Long id) {
        log.info("Soft deleting ProcessedFundIngress with ID: {}", id);

        boolean deleted = processedFundIngressService.softProcessedFundIngressServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("ProcessedFundIngress soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("ProcessedFundIngress soft deletion failed - ID: " + id);
        }
    }

}
