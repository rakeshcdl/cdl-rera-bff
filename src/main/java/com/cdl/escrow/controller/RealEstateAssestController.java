package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.RealEstateAssestCriteria;
import com.cdl.escrow.criteriaservice.RealEstateAssestCriteriaService;
import com.cdl.escrow.dto.BuildPartnerBeneficiaryDTO;
import com.cdl.escrow.dto.RealEstateAssestDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.RealEstateAssestRepository;
import com.cdl.escrow.service.RealEstateAssestService;
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
@RequestMapping("/api/v1/real-estate-assest")
@RequiredArgsConstructor
@Slf4j
public class RealEstateAssestController {

     private final RealEstateAssestService realEstateAssestService;

     private final RealEstateAssestCriteriaService realEstateAssestCriteriaService;

    private final RealEstateAssestRepository repository;

    private static final String ENTITY_NAME = "REAL_ESTATE_ASSEST";

    @GetMapping
    public ResponseEntity<List<RealEstateAssestDTO>> getAllRealEstateAssestByCriteria(@ParameterObject RealEstateAssestCriteria criteria,
                                                                                                    @ParameterObject  Pageable pageable) {
        Page<RealEstateAssestDTO> page = realEstateAssestCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<RealEstateAssestDTO>> getAllRealEstateAssest(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all real estate assest, page: {}", pageable.getPageNumber());
        Page<RealEstateAssestDTO> page = realEstateAssestService.getAllRealEstateAssest(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<RealEstateAssestDTO> saveRealEstateAssest(
            @Valid @RequestBody RealEstateAssestDTO dto) {
        log.info("Creating new real estate assest ");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new real estate assests cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RealEstateAssestDTO saved = realEstateAssestService.saveRealEstateAssest(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RealEstateAssestDTO> getRealEstateAssestById(@PathVariable Long id) {
        log.info("Fetching real estate assest with ID: {}", id);
        return realEstateAssestService.getRealEstateAssestById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Real estate assest not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<RealEstateAssestDTO> updateRealEstateAssest(
            @PathVariable Long id,
            @Valid @RequestBody RealEstateAssestDTO dto) {
        log.info("Updating real estate assest with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RealEstateAssestDTO updated = realEstateAssestService.updateRealEstateAssest(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRealEstateAssestById(@PathVariable Long id) {
        log.info("Deleting real estate assest with ID: {}", id);
        boolean deleted = realEstateAssestService.deleteRealEstateAssestById(id);
        if (deleted) {
            return ResponseEntity.ok("RealEstateAssest deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("RealEstateAssest deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteRealEstateAssestServiceById(@PathVariable Long id) {
        log.info("Soft deleting RealEstateAssest with ID: {}", id);

        boolean deleted = realEstateAssestService.softRealEstateAssestServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("RealEstateAssest soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("RealEstateAssest soft deletion failed - ID: " + id);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<RealEstateAssestDTO> uploadRealEstateAssestTemplate(
            @RequestParam("file") MultipartFile file) {
        log.info("Bulk upload build RealEstate Assest");

       /* if (dto.getId() != null) {
            throw new BadRequestAlertException("A new build partner cannot already have an ID", ENTITY_NAME, "idexists");
        }*/
        // BuildPartnerDTO saved = buildPartnerService.saveBuildPartner(dto);
        // return ResponseEntity.ok(saved);
        return null;
    }
}
