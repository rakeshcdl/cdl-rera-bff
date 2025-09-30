package com.cdl.escrow.controller;

import com.cdl.escrow.criteria.CapitalPartnerUnitTypeCriteria;
import com.cdl.escrow.criteriaservice.CapitalPartnerUnitTypeCriteriaService;
import com.cdl.escrow.dto.CapitalPartnerUnitTypeDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.CapitalPartnerUnitTypeRepository;
import com.cdl.escrow.service.CapitalPartnerUnitTypeService;
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
@RequestMapping("/api/v1/capital-partner-unit-type")
@RequiredArgsConstructor
@Slf4j
public class CapitalPartnerUnitTypeController {

    private final CapitalPartnerUnitTypeService capitalPartnerUnitTypeService;

    private final CapitalPartnerUnitTypeCriteriaService capitalPartnerUnitTypeCriteriaService;

    private final CapitalPartnerUnitTypeRepository repository;

    private static final String ENTITY_NAME = "CAPITAL_PARTNER_UNIT_TYPE";

    @GetMapping
    public ResponseEntity<Page<CapitalPartnerUnitTypeDTO>> getAllCapitalPartnerUnitTypesByCriteria(@ParameterObject CapitalPartnerUnitTypeCriteria criteria,
                                                                                                          @ParameterObject  Pageable pageable) {
        Page<CapitalPartnerUnitTypeDTO> page = capitalPartnerUnitTypeCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<CapitalPartnerUnitTypeDTO>> getAllCapitalPartnerUnitTypes(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all capital partner unit type, page: {}", pageable.getPageNumber());
        Page<CapitalPartnerUnitTypeDTO> page = capitalPartnerUnitTypeService.getAllCapitalPartnerUnitType(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<CapitalPartnerUnitTypeDTO> saveCapitalPartnerUnitType(
            @Valid @RequestBody CapitalPartnerUnitTypeDTO dto) {
        log.info("Creating new capital partner unit type");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new capital partner unit type cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CapitalPartnerUnitTypeDTO saved = capitalPartnerUnitTypeService.saveCapitalPartnerUnitType(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CapitalPartnerUnitTypeDTO> getCapitalPartnerUnitTypeById(@PathVariable Long id) {
        log.info("Fetching capital partner unit type with ID: {}", id);
        return capitalPartnerUnitTypeService.getCapitalPartnerUnitTypeById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Capital partner unit type not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<CapitalPartnerUnitTypeDTO> updateCapitalPartnerUnitType(
            @PathVariable Long id,
            @Valid @RequestBody CapitalPartnerUnitTypeDTO dto) {
        log.info("Updating capital partner unit type with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        CapitalPartnerUnitTypeDTO updated = capitalPartnerUnitTypeService.updateCapitalPartnerUnitType(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCapitalPartnerUnitTypeById(@PathVariable Long id) {
        log.info("Deleting capital partner unit type with ID: {}", id);
        boolean deleted = capitalPartnerUnitTypeService.deleteCapitalPartnerUnitTypeById(id);
        if (deleted) {
            return ResponseEntity.ok("CapitalPartnerUnitType deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("CapitalPartnerUnitType deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteCapitalPartnerUnitTypeServiceById(@PathVariable Long id) {
        log.info("Soft deleting CapitalPartnerUnitType with ID: {}", id);

        boolean deleted = capitalPartnerUnitTypeService.softCapitalPartnerUnitTypeServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("CapitalPartnerUnitType soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("CapitalPartnerUnitType soft deletion failed - ID: " + id);
        }
    }
}
