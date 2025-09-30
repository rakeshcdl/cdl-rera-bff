package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.CapitalPartnerCriteria;
import com.cdl.escrow.criteriaservice.CapitalPartnerCriteriaService;
import com.cdl.escrow.dto.CapitalPartnerDTO;
import com.cdl.escrow.dto.RealEstateAssestBeneficiaryDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.CapitalPartnerRepository;
import com.cdl.escrow.service.CapitalPartnerService;
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
@RequestMapping("/api/v1/capital-partner")
@RequiredArgsConstructor
@Slf4j
public class CapitalPartnerController {

    private final CapitalPartnerService capitalPartnerService;

    private final CapitalPartnerCriteriaService capitalPartnerCriteriaService;

    private final CapitalPartnerRepository repository;

    private static final String ENTITY_NAME = "CAPITAL_PARTNER";

    @GetMapping
    public ResponseEntity<Page<CapitalPartnerDTO>> getAllCapitalPartnersByCriteria(@ParameterObject CapitalPartnerCriteria criteria,
                                                                                                   @ParameterObject  Pageable pageable) {
        Page<CapitalPartnerDTO> page = capitalPartnerCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<CapitalPartnerDTO>> getAllCapitalPartners(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all capital partner, page: {}", pageable.getPageNumber());
        Page<CapitalPartnerDTO> page = capitalPartnerService.getAllCapitalPartner(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<CapitalPartnerDTO> saveCapitalPartner(
            @Valid @RequestBody CapitalPartnerDTO dto) {
        log.info("Creating new capital partner");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new capital partner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CapitalPartnerDTO saved = capitalPartnerService.saveCapitalPartner(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CapitalPartnerDTO> getCapitalPartnerById(@PathVariable Long id) {
        log.info("Fetching capital partner with ID: {}", id);
        return capitalPartnerService.getCapitalPartnerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Capital partner not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<CapitalPartnerDTO> updateCapitalPartner(
            @PathVariable Long id,
            @Valid @RequestBody CapitalPartnerDTO dto) {
        log.info("Updating capital partner with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        CapitalPartnerDTO updated = capitalPartnerService.updateCapitalPartner(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCapitalPartnerById(@PathVariable Long id) {
        log.info("Deleting capital partner with ID: {}", id);
        boolean deleted = capitalPartnerService.deleteCapitalPartnerById(id);
        if (deleted) {
            return ResponseEntity.ok("CapitalPartner deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("CapitalPartner deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteCapitalPartnerServiceById(@PathVariable Long id) {
        log.info("Soft deleting CapitalPartner with ID: {}", id);

        boolean deleted = capitalPartnerService.softCapitalPartnerServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("CapitalPartner soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("CapitalPartner soft deletion failed - ID: " + id);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<CapitalPartnerDTO> uploadCapitalPartnerTemplate(
            @RequestParam("file") MultipartFile file) {
        log.info("Bulk upload build capital partner");

       /* if (dto.getId() != null) {
            throw new BadRequestAlertException("A new build partner cannot already have an ID", ENTITY_NAME, "idexists");
        }*/
        // BuildPartnerDTO saved = buildPartnerService.saveBuildPartner(dto);
        // return ResponseEntity.ok(saved);
        return null;
    }
}
