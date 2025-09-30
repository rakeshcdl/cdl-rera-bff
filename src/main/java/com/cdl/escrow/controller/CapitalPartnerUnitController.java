package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.CapitalPartnerUnitCriteria;
import com.cdl.escrow.criteriaservice.CapitalPartnerUnitCriteriaService;
import com.cdl.escrow.dto.CapitalPartnerDTO;
import com.cdl.escrow.dto.CapitalPartnerUnitDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.CapitalPartnerUnitRepository;
import com.cdl.escrow.service.CapitalPartnerUnitService;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api/v1/capital-partner-unit")
@RequiredArgsConstructor
@Slf4j
public class CapitalPartnerUnitController {

   private final CapitalPartnerUnitService capitalPartnerUnitService;

   private final CapitalPartnerUnitCriteriaService capitalPartnerUnitCriteriaService;

    private final CapitalPartnerUnitRepository repository;

    private static final String ENTITY_NAME = "CAPITAL_PARTNER_UNIT";

    @GetMapping
    public ResponseEntity<List<CapitalPartnerUnitDTO>> getAllCapitalPartnerUnitsByCriteria(@ParameterObject CapitalPartnerUnitCriteria criteria,
                                                                                                         @ParameterObject  Pageable pageable) {
        Page<CapitalPartnerUnitDTO> page = capitalPartnerUnitCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<CapitalPartnerUnitDTO>> getAllCapitalPartnerUnits(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all capital partner unit, page: {}", pageable.getPageNumber());
        Page<CapitalPartnerUnitDTO> page = capitalPartnerUnitService.getAllCapitalPartnerUnit(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<CapitalPartnerUnitDTO> saveCapitalPartnerUnit(
            @Valid @RequestBody CapitalPartnerUnitDTO dto) {
        log.info("Creating new capital partner unit ");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new capital partner unit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CapitalPartnerUnitDTO saved = capitalPartnerUnitService.saveCapitalPartnerUnit(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CapitalPartnerUnitDTO> getCapitalPartnerUnitById(@PathVariable Long id) {
        log.info("Fetching capital partner unit  with ID: {}", id);
        return capitalPartnerUnitService.getCapitalPartnerUnitById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Capital partner unit  not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<CapitalPartnerUnitDTO> updateCapitalPartnerUnit(
            @PathVariable Long id,
            @Valid @RequestBody CapitalPartnerUnitDTO dto) {
        log.info("Updating capital partner unit with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        CapitalPartnerUnitDTO updated = capitalPartnerUnitService.updateCapitalPartnerUnit(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCapitalPartnerUnitById(@PathVariable Long id) {
        log.info("Deleting capital partner unit with ID: {}", id);
        boolean deleted = capitalPartnerUnitService.deleteCapitalPartnerUnitById(id);
        if (deleted) {
            return ResponseEntity.ok("CapitalPartnerUnit deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("CapitalPartnerUnit deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteCapitalPartnerUnitServiceById(@PathVariable Long id) {
        log.info("Soft deleting CapitalPartnerUnit with ID: {}", id);

        boolean deleted = capitalPartnerUnitService.softCapitalPartnerUnitServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("CapitalPartnerUnit soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("CapitalPartnerUnit soft deletion failed - ID: " + id);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<CapitalPartnerUnitDTO> uploadCapitalPartnerTemplate(
            @RequestParam("file") MultipartFile file) {
        log.info("Bulk upload build capital partner Unit");

       /* if (dto.getId() != null) {
            throw new BadRequestAlertException("A new build partner cannot already have an ID", ENTITY_NAME, "idexists");
        }*/
        // BuildPartnerDTO saved = buildPartnerService.saveBuildPartner(dto);
        // return ResponseEntity.ok(saved);
        return null;
    }


}
