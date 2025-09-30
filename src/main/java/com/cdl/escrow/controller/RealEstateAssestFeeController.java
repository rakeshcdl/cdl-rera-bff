package com.cdl.escrow.controller;

import com.cdl.escrow.criteria.RealEstateAssestFeeCriteria;
import com.cdl.escrow.criteriaservice.RealEstateAssestFeeCriteriaService;
import com.cdl.escrow.dto.RealEstateAssestFeeDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.RealEstateAssestFeeRepository;
import com.cdl.escrow.service.RealEstateAssestFeeService;
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
@RequestMapping("/api/v1/real-estate-asset-fee")
@RequiredArgsConstructor
@Slf4j
public class RealEstateAssestFeeController {

    private final RealEstateAssestFeeService realEstateAssestFeeService;

    private final RealEstateAssestFeeCriteriaService realEstateAssestFeeCriteriaService;

    private final RealEstateAssestFeeRepository repository;

    private static final String ENTITY_NAME = "REAL_ESTATE_FEE";

    @GetMapping
    public ResponseEntity<Page<RealEstateAssestFeeDTO>> getAllRealEstateAssestFeeByCriteria(@ParameterObject RealEstateAssestFeeCriteria criteria,
                                                                                      @ParameterObject  Pageable pageable) {
        Page<RealEstateAssestFeeDTO> page = realEstateAssestFeeCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<RealEstateAssestFeeDTO>> getAllRealEstateAssestFee(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all real estate assest fee, page: {}", pageable.getPageNumber());
        Page<RealEstateAssestFeeDTO> page = realEstateAssestFeeService.getAllRealEstateAssestFee(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<RealEstateAssestFeeDTO> saveRealEstateAssestFee(
            @Valid @RequestBody RealEstateAssestFeeDTO dto) {
        log.info("Creating new real estate assest fee");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new real estate assest fee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RealEstateAssestFeeDTO saved = realEstateAssestFeeService.saveRealEstateAssestFee(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RealEstateAssestFeeDTO> getRealEstateAssestFeeById(@PathVariable Long id) {
        log.info("Fetching real estate assest fee with ID: {}", id);
        return realEstateAssestFeeService.getRealEstateAssestFeeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Real estate assest fee not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<RealEstateAssestFeeDTO> updateRealEstateAssestFee(
            @PathVariable Long id,
            @Valid @RequestBody RealEstateAssestFeeDTO dto) {
        log.info("Updating real estate assest fee with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RealEstateAssestFeeDTO updated = realEstateAssestFeeService.updateRealEstateAssestFee(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRealEstateAssestFeeById(@PathVariable Long id) {
        log.info("Deleting real estate assest fee with ID: {}", id);
        boolean deleted = realEstateAssestFeeService.deleteRealEstateAssestFeeById(id);
        if (deleted) {
            return ResponseEntity.ok("RealEstateAssestFee deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("RealEstateAssestFee deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteRealEstateAssestFeeServiceById(@PathVariable Long id) {
        log.info("Soft deleting RealEstateAssestFee with ID: {}", id);

        boolean deleted = realEstateAssestFeeService.softRealEstateAssestFeeServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("RealEstateAssestFee soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("RealEstateAssestFee soft deletion failed - ID: " + id);
        }
    }
}
