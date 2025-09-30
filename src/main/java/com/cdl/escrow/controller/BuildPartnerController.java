package com.cdl.escrow.controller;


import com.cdl.escrow.criteria.BuildPartnerCriteria;
import com.cdl.escrow.criteriaservice.BuildPartnerCriteriaService;
import com.cdl.escrow.dto.BuildPartnerDTO;
import com.cdl.escrow.dto.record.BuildPartnerRecord;
import com.cdl.escrow.exception.BadRequestAlertException;
import com.cdl.escrow.helper.PaginationUtil;
import com.cdl.escrow.repository.BuildPartnerRepository;
import com.cdl.escrow.service.BuildPartnerService;
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
@RequestMapping("/api/v1/build-partner")
@RequiredArgsConstructor
@Slf4j
public class BuildPartnerController {

    private final BuildPartnerService buildPartnerService;

    private final BuildPartnerCriteriaService buildPartnerCriteriaService;

    private final BuildPartnerRepository repository;

    private static final String ENTITY_NAME = "BUILD_PARTNER";

    @GetMapping
    public ResponseEntity<List<BuildPartnerDTO>> getAllBuildPartnersByCriteria(@ParameterObject BuildPartnerCriteria criteria,
                                                                                             @ParameterObject  Pageable pageable) {
        Page<BuildPartnerDTO> page = buildPartnerCriteriaService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/find-all")
    public ResponseEntity<Page<BuildPartnerDTO>> getAllBuildPartners(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching all build partner , page: {}", pageable.getPageNumber());
        Page<BuildPartnerDTO> page = buildPartnerService.getAllBuildPartner(pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity<BuildPartnerDTO> saveBuildPartner(
            @Valid @RequestBody BuildPartnerDTO dto) {
        log.info("Creating new build partner");

        if (dto.getId() != null) {
            throw new BadRequestAlertException("A new build partner cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BuildPartnerDTO saved = buildPartnerService.saveBuildPartner(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuildPartnerDTO> getBuildPartnerById(@PathVariable Long id) {
        log.info("Fetching build partner with ID: {}", id);
        return buildPartnerService.getBuildPartnerById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Build partner not found for ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuildPartnerDTO> updateBuildPartner(
            @PathVariable Long id,
            @Valid @RequestBody BuildPartnerDTO dto) {
        log.info("Updating build partner with ID: {}", id);
        if (dto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dto.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!repository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        BuildPartnerDTO updated = buildPartnerService.updateBuildPartner(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBuildPartnerById(@PathVariable Long id) {
        log.info("Deleting build partner with ID: {}", id);
        boolean deleted = buildPartnerService.deleteBuildPartnerById(id);
        if (deleted) {
            return ResponseEntity.ok("BuildPartner deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("BuildPartner deletion failed - ID: " + id);
        }
    }

    @DeleteMapping("/soft/{id}")
    public ResponseEntity<String> softDeleteBuildPartnerServiceById(@PathVariable Long id) {
        log.info("Soft deleting BuildPartner with ID: {}", id);

        boolean deleted = buildPartnerService.softBuildPartnerContactServiceById(id);
        if (deleted) {
            return ResponseEntity.ok("BuildPartner soft deleted - ID: " + id);
        } else {
            return ResponseEntity.badRequest().body("BuildPartner soft deletion failed - ID: " + id);
        }
    }

    //Get api for all data

    @GetMapping("/data/{id}")
    public ResponseEntity<BuildPartnerRecord> getBuildPartnerDataById(@PathVariable Long id) {
        log.info("Fetching build partner data with ID: {}", id);
       return null;
    }

    @PostMapping("/upload")
    public ResponseEntity<BuildPartnerDTO> uploadBuildPartnerTemplate(
            @RequestParam("file") MultipartFile file) {
        log.info("Bulk upload build partner");

       /* if (dto.getId() != null) {
            throw new BadRequestAlertException("A new build partner cannot already have an ID", ENTITY_NAME, "idexists");
        }*/
       // BuildPartnerDTO saved = buildPartnerService.saveBuildPartner(dto);
       // return ResponseEntity.ok(saved);
        return null;
    }
}
