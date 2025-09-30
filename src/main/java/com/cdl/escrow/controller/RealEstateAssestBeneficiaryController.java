package com.cdl.escrow.controller;



import com.cdl.escrow.criteria.RealEstateAssestBeneficiaryCriteria;
import com.cdl.escrow.criteriaservice.RealEstateAssestBeneficiaryCriteriaService;
import com.cdl.escrow.dto.RealEstateAssestBeneficiaryDTO;
import com.cdl.escrow.dto.RealEstateAssestDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.RealEstateAssestBeneficiaryRepository;
import com.cdl.escrow.service.RealEstateAssestBeneficiaryService;
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
@RequestMapping("/api/v1/real-estate-assest-beneficiary")
@RequiredArgsConstructor
@Slf4j
public class RealEstateAssestBeneficiaryController {
    private final RealEstateAssestBeneficiaryService realEstateAssestBeneficiaryService;

    private final RealEstateAssestBeneficiaryCriteriaService realEstateAssestBeneficiaryCriteriaService;

    private final RealEstateAssestBeneficiaryRepository repository;

    private static final String ENTITY_NAME = "REAL_ESTATE_ASSEST_BENEFICIARY";

    @GetMapping
    public ResponseEntity<Page<RealEstateAssestBeneficiaryDTO>> getAllRealEstateAssestBeneficiaryByCriteria(@ParameterObject RealEstateAssestBeneficiaryCriteria criteria,
                                                                                          @ParameterObject  Pageable pageable) {
        Page<RealEstateAssestBeneficiaryDTO> page = realEstateAssestBeneficiaryCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<RealEstateAssestBeneficiaryDTO>> getAllRealEstateAssestBeneficiary(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all real estate assest beneficiary, page: {}", pageable.getPageNumber());
        Page<RealEstateAssestBeneficiaryDTO> page = realEstateAssestBeneficiaryService.getAllRealEstateAssestBeneficiary(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<RealEstateAssestBeneficiaryDTO> saveRealEstateAssestBeneficiary(
            @Valid @RequestBody RealEstateAssestBeneficiaryDTO dto) {
        log.info("Creating new real estate assest beneficiary");
        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new real estate beneficiary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RealEstateAssestBeneficiaryDTO saved = realEstateAssestBeneficiaryService.saveRealEstateAssestBeneficiary(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RealEstateAssestBeneficiaryDTO> getRealEstateAssestBeneficiaryById(@PathVariable Long id) {
        log.info("Fetching real estate assest beneficiary with ID: {}", id);
        return realEstateAssestBeneficiaryService.getRealEstateAssestBeneficiaryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Real estate assest beneficiary not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<RealEstateAssestBeneficiaryDTO> updateRealEstateAssestBeneficiary(
            @PathVariable Long id,
            @Valid @RequestBody RealEstateAssestBeneficiaryDTO dto) {
        log.info("Updating real estate assest beneficiary with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RealEstateAssestBeneficiaryDTO updated = realEstateAssestBeneficiaryService.updateRealEstateAssestBeneficiary(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRealEstateAssestBeneficiaryById(@PathVariable Long id) {
        log.info("Deleting real estate assest beneficiary with ID: {}", id);
        boolean deleted = realEstateAssestBeneficiaryService.deleteRealEstateAssestBeneficiaryById(id);
        if (deleted) {
            return ResponseEntity.ok("RealEstateAssestBeneficiary deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("RealEstateAssestBeneficiary deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteRealEstateAssestBeneficiaryServiceById(@PathVariable Long id) {
        log.info("Soft deleting RealEstateAssestBeneficiary with ID: {}", id);

        boolean deleted = realEstateAssestBeneficiaryService.softRealEstateAssestBeneficiaryServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("RealEstateAssestBeneficiary soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("RealEstateAssestBeneficiary soft deletion failed - ID: " + id);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<RealEstateAssestBeneficiaryDTO> uploadRealEstateAssestBeneficiaryTemplate(
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
