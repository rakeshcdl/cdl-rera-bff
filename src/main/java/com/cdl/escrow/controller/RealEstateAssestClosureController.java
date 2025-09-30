package com.cdl.escrow.controller;

import com.cdl.escrow.criteria.RealEstateAssestClosureCriteria;
import com.cdl.escrow.criteriaservice.RealEstateAssestClosureCriteriaService;
import com.cdl.escrow.dto.RealEstateAssestClosureDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.RealEstateAssestClosureRepository;
import com.cdl.escrow.service.RealEstateAssestClosureService;
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
@RequestMapping("/api/v1/real-estate-assest-closure")
@RequiredArgsConstructor
@Slf4j
public class RealEstateAssestClosureController {

   private final RealEstateAssestClosureService realEstateAssestClosureService;

   private final RealEstateAssestClosureCriteriaService realEstateAssestClosureCriteriaService;

    private final RealEstateAssestClosureRepository repository;

    private static final String ENTITY_NAME = "REAL_ESTATE_ASSEST_CLOSURE";

    @GetMapping
    public ResponseEntity<Page<RealEstateAssestClosureDTO>> getAllRealEstateAssestClosureByCriteria(@ParameterObject RealEstateAssestClosureCriteria criteria,
                                                                                                            @ParameterObject  Pageable pageable) {
        Page<RealEstateAssestClosureDTO> page = realEstateAssestClosureCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<RealEstateAssestClosureDTO>> getAllRealEstateAssestClosure(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all real estate assest closure, page: {}", pageable.getPageNumber());
        Page<RealEstateAssestClosureDTO> page = realEstateAssestClosureService.getAllRealEstateAssestClosure(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<RealEstateAssestClosureDTO> saveRealEstateAssestClosure(
            @Valid @RequestBody RealEstateAssestClosureDTO dto) {
        log.info("Creating new real estate assest closure");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new real estate closure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RealEstateAssestClosureDTO saved = realEstateAssestClosureService.saveRealEstateAssestClosure(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RealEstateAssestClosureDTO> getRealEstateAssestClosureById(@PathVariable Long id) {
        log.info("Fetching real estate assest closure with ID: {}", id);
        return realEstateAssestClosureService.getRealEstateAssestClosureById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Real estate assest closure not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<RealEstateAssestClosureDTO> updateRealEstateAssestClosure(
            @PathVariable Long id,
            @Valid @RequestBody RealEstateAssestClosureDTO dto) {
        log.info("Updating real estate assest closure with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RealEstateAssestClosureDTO updated = realEstateAssestClosureService.updateRealEstateAssestClosure(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRealEstateAssestClosureById(@PathVariable Long id) {
        log.info("Deleting real estate assest closure with ID: {}", id);
        boolean deleted = realEstateAssestClosureService.deleteRealEstateAssestClosureById(id);
        if (deleted) {
            return ResponseEntity.ok("RealEstateAssestClosure deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("RealEstateAssestClosure deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteRealEstateAssestClosureServiceById(@PathVariable Long id) {
        log.info("Soft deleting RealEstateAssestClosure with ID: {}", id);

        boolean deleted = realEstateAssestClosureService.softRealEstateAssestClosureServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("RealEstateAssestClosure soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("RealEstateAssestClosure soft deletion failed - ID: " + id);
        }
    }

}
