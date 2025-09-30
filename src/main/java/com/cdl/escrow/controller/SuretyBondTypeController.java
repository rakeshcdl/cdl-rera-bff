package com.cdl.escrow.controller;

import com.cdl.escrow.criteria.SuretyBondTypeCriteria;
import com.cdl.escrow.criteriaservice.SuretyBondTypeCriteriaService;
import com.cdl.escrow.dto.SuretyBondTypeDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.SuretyBondTypeRepository;
import com.cdl.escrow.service.SuretyBondTypeService;
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
@RequestMapping("/api/v1/surety-bond-type")
@RequiredArgsConstructor
@Slf4j
public class SuretyBondTypeController {

    private final SuretyBondTypeService suretyBondTypeService;

    private final SuretyBondTypeCriteriaService suretyBondTypeCriteriaService;

    private final SuretyBondTypeRepository repository;

    private static final String ENTITY_NAME = "SURETY_BOND_TYPE";

    @GetMapping
    public ResponseEntity<Page<SuretyBondTypeDTO>> getAllSuretyBondTypeByCriteria(@ParameterObject SuretyBondTypeCriteria criteria,
                                                                                         @ParameterObject  Pageable pageable) {
        Page<SuretyBondTypeDTO> page = suretyBondTypeCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }


    @GetMapping("/find-all")
    public ResponseEntity<Page<SuretyBondTypeDTO>> getAllSuretyBondType(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all surety bond type, page: {}", pageable.getPageNumber());
        Page<SuretyBondTypeDTO> page = suretyBondTypeService.getAllSuretyBondType(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<SuretyBondTypeDTO> saveSuretyBondType(
            @Valid @RequestBody SuretyBondTypeDTO dto) {
        log.info("Creating new surety bond type");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new surety bond type cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SuretyBondTypeDTO saved = suretyBondTypeService.saveSuretyBondType(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuretyBondTypeDTO> getSuretyBondTypeById(@PathVariable Long id) {
        log.info("Fetching surety bond type with ID: {}", id);
        return suretyBondTypeService.getSuretyBondTypeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Surety bond type not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuretyBondTypeDTO> updateSuretyBondType(
            @PathVariable Long id,
            @Valid @RequestBody SuretyBondTypeDTO dto) {
        log.info("Updating surety bond type with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        SuretyBondTypeDTO updated = suretyBondTypeService.updateSuretyBondType(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSuretyBondTypeById(@PathVariable Long id) {
        log.info("Deleting surety bond type with ID: {}", id);
        boolean deleted = suretyBondTypeService.deleteSuretyBondTypeById(id);
        if (deleted) {
            return ResponseEntity.ok("SuretyBondType deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("SuretyBondType deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteSuretyBondTypeServiceById(@PathVariable Long id) {
        log.info("Soft deleting SuretyBondType with ID: {}", id);

        boolean deleted = suretyBondTypeService.softSuretyBondTypeServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("SuretyBondType soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("SuretyBondType soft deletion failed - ID: " + id);
        }
    }
}
