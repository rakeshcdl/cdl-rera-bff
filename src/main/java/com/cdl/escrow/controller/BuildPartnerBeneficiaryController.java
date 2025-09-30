package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.BuildPartnerBeneficiaryCriteria;
import com.cdl.escrow.criteriaservice.BuildPartnerBeneficiaryCriteriaService;
import com.cdl.escrow.dto.BuildPartnerBeneficiaryDTO;
import com.cdl.escrow.dto.BuildPartnerDTO;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.BuildPartnerBeneficiaryRepository;
import com.cdl.escrow.service.BuildPartnerBeneficiaryService;
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
@RequestMapping("/api/v1/build-partner-beneficiary")
@RequiredArgsConstructor
@Slf4j
public class BuildPartnerBeneficiaryController {

   private final BuildPartnerBeneficiaryService buildPartnerBeneficiaryService;

   private final BuildPartnerBeneficiaryCriteriaService buildPartnerBeneficiaryCriteriaService;

    private final BuildPartnerBeneficiaryRepository repository;

    private static final String ENTITY_NAME = "BUILD_PARTNER_BENEFICIARY";

    @GetMapping
    public ResponseEntity<Page<BuildPartnerBeneficiaryDTO>> getAllBuildPartnerBeneficiaryByCriteria(@ParameterObject BuildPartnerBeneficiaryCriteria criteria,
                                                                                             @ParameterObject  Pageable pageable) {
        Page<BuildPartnerBeneficiaryDTO> page = buildPartnerBeneficiaryCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }


    @GetMapping("/find-all")
    public ResponseEntity<Page<BuildPartnerBeneficiaryDTO>> getAllBuildPartnerBeneficiary(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all build partner beneficiary , page: {}", pageable.getPageNumber());
        Page<BuildPartnerBeneficiaryDTO> page = buildPartnerBeneficiaryService.getAllBuildPartnerBeneficiary(pageable);
        return ResponseEntity.ok(page);
    }
    @PostMapping
    public ResponseEntity<BuildPartnerBeneficiaryDTO> saveBuildPartnerBeneficiary(
            @Valid @RequestBody BuildPartnerBeneficiaryDTO dto) {
        log.info("Creating new build partner beneficiary");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new build partner beneficiary cannot already have an ID",ENTITY_NAME, "idexists");
        }
        BuildPartnerBeneficiaryDTO saved = buildPartnerBeneficiaryService.saveBuildPartnerBeneficiary(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildPartnerBeneficiaryDTO> getBuildPartnerBeneficiaryById(@PathVariable Long id) {
        log.info("Fetching build partner beneficiary with ID: {}", id);
        return buildPartnerBeneficiaryService.getBuildPartnerBeneficiaryById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Build partner beneficiary not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuildPartnerBeneficiaryDTO> updateBuildPartnerBeneficiary(
            @PathVariable Long id,
            @Valid @RequestBody BuildPartnerBeneficiaryDTO dto) {
        log.info("Updating build partner beneficiary with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        BuildPartnerBeneficiaryDTO updated = buildPartnerBeneficiaryService.updateBuildPartnerBeneficiary(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBuildPartnerBeneficiaryById(@PathVariable Long id) {
        log.info("Deleting build partner beneficiary with ID: {}", id);
        boolean deleted = buildPartnerBeneficiaryService.deleteBuildPartnerBeneficiaryById(id);
        if (deleted) {
            return ResponseEntity.ok("BuildPartnerBeneficiary deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("BuildPartnerBeneficiary deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteBuildPartnerBeneficiaryServiceById(@PathVariable Long id) {
        log.info("Soft deleting BuildPartnerBeneficiary with ID: {}", id);

        boolean deleted = buildPartnerBeneficiaryService.softBuildPartnerBeneficiaryServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("BuildPartnerBeneficiary soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("BuildPartnerBeneficiary soft deletion failed - ID: " + id);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<BuildPartnerBeneficiaryDTO> uploadBuildPartnerBeneficiaryTemplate(
            @RequestParam("file") MultipartFile file) {
        log.info("Bulk upload build partner beneficiary");

       /* if (dto.getId() != null) {
            throw new BadRequestAlertException("A new build partner cannot already have an ID", ENTITY_NAME, "idexists");
        }*/
        // BuildPartnerDTO saved = buildPartnerService.saveBuildPartner(dto);
        // return ResponseEntity.ok(saved);
        return null;
    }

}
